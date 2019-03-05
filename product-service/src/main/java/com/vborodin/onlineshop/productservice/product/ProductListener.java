package com.vborodin.onlineshop.productservice.product;

import com.vborodin.onlineshop.productservice.product.history.ActionType;
import com.vborodin.onlineshop.productservice.product.history.ProductHistory;
import com.vborodin.onlineshop.productservice.util.BeanUtil;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class ProductListener {
    @PrePersist
    public void prePersist(Product product) {
        perform(product, ActionType.INSERT);
    }

    @PreUpdate
    public void preUpdate(Product product) {
        perform(product, ActionType.UPDATE);
    }

    @PreRemove
    public void preRemove(Product product) {
        perform(product, ActionType.DELETE);
    }

    private void perform(Product product, ActionType action) {
        EntityManager em = BeanUtil.getBean(EntityManager.class);
        em.persist(new ProductHistory(product, action));
    }
}