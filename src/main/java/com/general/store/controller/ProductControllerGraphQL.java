package com.general.store.controller;

import com.general.store.model.dao.impl.Comment;
import com.general.store.model.dto.entityanalogue.impl.ProductDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller // endpoint na graphql jest tylko 1
public class ProductControllerGraphQL {
    // GraphQL ma dwie operacje mutacja (sluzy do manipulacja danymi np tworzenia) i query (sluzy do pobieranie danych)


    // roznica taka ze wykonujesze 1 request zamiast 2 jak np chcesz jednoczessnie wyswietlic produkty i idk cos zwiazanego z produktem
    // daje to mniejszy ruch -> w razie problemów mniej przeglądania
    @QueryMapping
    public ProductDTO getProduct(@Argument Long id){
        return ProductDTO.builder()
                .id(id)
                .name("sdkbsu")
                .description("description")
                .quantity(999L)
                .score(.9)
                .lastModifiedBy("lastModifiedBy")
                .comments(Arrays.asList(
                        Comment.builder().score(.9).content("content1").build(),
                        Comment.builder().score(.1).content("content2").build()
                        ))
                .build();
        // graphql w angularze obsługuje biblioteka apollo
    }

    @QueryMapping
    public List<Comment> getComments() {
        return Arrays.asList(
                Comment.builder().score(.9).content("content1").build(),
                Comment.builder().score(.1).content("content2").build()
        );
    }

    @MutationMapping
    public ProductDTO save(@Argument ProductDTO productDTO){
        return productDTO;
    }
}
