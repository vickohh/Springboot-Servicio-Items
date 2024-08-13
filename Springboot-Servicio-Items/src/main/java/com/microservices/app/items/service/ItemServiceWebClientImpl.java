package com.microservices.app.items.service;

import com.microservices.app.items.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
//@Primary
public class ItemServiceWebClientImpl implements  ItemService{


    @Autowired
    private WebClient.Builder loadBalancedWebClientBuilder;

    @Override
    public List<Item> findAll() {

        List<Item> itemList = new ArrayList<>();

//        Mono<List<Item>> itemsMono =  loadBalancedWebClientBuilder.build()
//                .get()
//                .uri("http://servicio-productos/api/productos/listar")
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<Item>>() {
//                });

        WebClient webClient = loadBalancedWebClientBuilder.build();

//            Mono<List<Item>> itemsMono = webClient.get()
//                .uri("http://servicio-productos/api/productos/listar")
//                .retrieve()
//                .bodyToFlux(Item.class)  // Obtiene un Flux de MyResponse
//                .collectList();  // Convierte el Flux en una lista

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://servicio-productos/api/productos/listar");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);


        Mono<List<Item>> itemsMono = webClient.get()
                .uri("http://servicio-productos/api/productos/listar")
                .retrieve()
                .onStatus(HttpStatus-> HttpStatus.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(errorBody ->
                                Mono.error(new RuntimeException("Error en la respuesta: " + errorBody)))
                )
                .bodyToFlux(Item.class)  // Obtiene un Flux de Item
                .collectList();  // Convierte el Flux en una lista


        itemsMono.subscribe(
                items -> {
                    // Aquí `items` es de tipo List<Item>
                    items.forEach(item -> System.out.println("recorriendo: " + item.toString()));

                    // Procesar la lista de Items (por ejemplo, imprimir cada Item)
//                    itemList2.forEach(item -> {
//                        System.out.println("recorriendo los productos 1 : " + item.getProducto()); // Usa el método toString() de Item
//                    });
                },
                error -> {
                    // Manejo de errores
                    System.err.println("Error occurred: " + error.getMessage());
                }
        );

        itemsMono.subscribe(
                items -> {
                    // Imprimir cada Item en la lista
                    items.forEach(item -> {
                        System.out.println("recorriendo los productos 2 : " +item.toString());
                    });
                },
                error -> {
                    // Manejo de errores
                    System.err.println("Error occurred: " + error.getMessage());
                }
        );

        return itemList;
    }

    @Override
    public Item findById(Long id, Integer cantidad) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://servicio-productos-ver/api/productos/ver/");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);

        WebClient webClient = loadBalancedWebClientBuilder.build();

        Mono<Item> itemsMono = webClient.get()
                .uri("http://servicio-productos-ver/api/productos/ver/"+ id.toString())
                .retrieve()
                .onStatus(HttpStatus-> HttpStatus.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(errorBody ->
                                Mono.error(new RuntimeException("Error en la respuesta: " + errorBody)))
                )
                .bodyToMono(Item.class) ;  // Convierte el Flux en una lista


        itemsMono.subscribe(
                item -> {
                    // Aquí `items` es de tipo List<Item>
                System.out.println("recorriendo: " + item.toString());

                },
                error -> {
                    // Manejo de errores
                    System.err.println("Error occurred: " + error.getMessage());
                }
        );

    return null;}
}
