package com.home;

import com.tata.jiuye.common.api.CommonPage;
import com.tata.jiuye.model.PmsProduct;
import com.tata.jiuye.portal.JiuyePortalApplication;
import com.tata.jiuye.portal.domain.HomeContentResult;
import com.tata.jiuye.portal.service.HomeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JiuyePortalApplication.class)
class HomeServiceImplTest {
    @Autowired
    private HomeService homeService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void content(){
        HomeContentResult result = homeService.content();
        System.out.println(result);
    }

    @Test
    void getPmsProductByProductCategoryId() {
        CommonPage<PmsProduct> pmsProductCommonPage = homeService.getPmsProductByProductCategoryId(1,10,19L);
        System.out.println(pmsProductCommonPage);
    }
}
