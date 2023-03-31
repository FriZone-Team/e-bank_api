package com.onionit.ebank.services;

import com.onionit.ebank.interfaces.KeySetting;
import com.onionit.ebank.interfaces.OnInitializer;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializerService implements InitializingBean {
    @Getter
    protected final SettingService settingService;

    @Getter
    protected final BeanFactory beanFactory;

    @Autowired
    public InitializerService(SettingService settingService, BeanFactory beanFactory) {
        this.settingService = settingService;
        this.beanFactory = beanFactory;
    }

    @Transactional
    public void afterPropertiesSet() {
        if (!this.settingService.isMarked(KeySetting.IS_SEEDED)) {
            for (OnInitializer initializer : this.getBeanFactory().getBeanProvider(OnInitializer.class)) {
                initializer.onInitializer();
            }
            this.settingService.mark(KeySetting.IS_SEEDED);
        }
    }
}
