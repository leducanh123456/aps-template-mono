package io.github.leducanh123456.feign;


import io.github.leducanh123456.config.feign.TestQuickOfferConfig;
import io.github.leducanh123456.feign.dto.QuickOfferBody;
import io.github.leducanh123456.feign.dto.QuickOfferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: hduong25
 */

@FeignClient(name = "f88QuickOfferClient",
        url = "${f88.api.quick_offer}",
        configuration = {TestQuickOfferConfig.class})
public interface TestQuickOfferApi {

    @PostMapping("/quick-offer")
    QuickOfferResponse quickOffer(@RequestBody QuickOfferBody input);

}
