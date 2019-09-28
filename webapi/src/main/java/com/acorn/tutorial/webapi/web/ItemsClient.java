package com.acorn.tutorial.webapi.web;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "items-service", fallbackFactory = ItemsClient.ItemsServiceFeignClientFallbackFactory.class)
@RibbonClient(name = "items-service")
public interface ItemsClient {

    @GetMapping("/items")
    List<Item> getItems();

    @GetMapping("/items/{id}")
    Item getItem(@PathVariable Long id);

    @Component
    class ItemsServiceFeignClientFallbackFactory implements FallbackFactory<ItemsClient> {

        @Override
        public ItemsClient create(Throwable throwable) {
            return new ItemsClient() {
                @Override
                public List<Item> getItems() {
                    return Collections.singletonList(new Item(0L, "ken", 0));
                }

                @Override
                public Item getItem(Long id) {
                    return new Item(0L, "ken", 0);
                }
            };
        }
    }
}