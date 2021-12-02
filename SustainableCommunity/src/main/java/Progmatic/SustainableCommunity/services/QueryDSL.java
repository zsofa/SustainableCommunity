package Progmatic.SustainableCommunity.services;


import Progmatic.SustainableCommunity.models.ItemCategory;
import Progmatic.SustainableCommunity.models.ItemCondition;
//import Progmatic.SustainableCommunity.models.QItem;
import Progmatic.SustainableCommunity.models.QItem;
import Progmatic.SustainableCommunity.models.QueryForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class QueryDSL {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void filterAndSortQueryDSL(QueryForm queryForm) {

         QItem item = QItem.item;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(!queryForm.getItemName().isBlank()) {
            booleanBuilder.and(item.itemName.eq(queryForm.getItemName()));
        }
        if(!queryForm.getItemCategory().isBlank()) {
            booleanBuilder.and(item.itemCategory.eq(ItemCategory.valueOf(queryForm.getItemCategory())));
        }
        if(!queryForm.getItemCondition().isBlank()) {
            booleanBuilder.and(item.itemCondition.eq(ItemCondition.valueOf(queryForm.getItemCondition())));
        }
        if(queryForm.getItemValueGreaterThan() != null) {
            booleanBuilder.and(item.itemValue.goe(queryForm.getItemValueGreaterThan()));
        }
        if(queryForm.getItemValueLesserThan() != null) {
            booleanBuilder.and(item.itemValue.loe(queryForm.getItemValueLesserThan()));
        }
        if(queryForm.getBorrowPriceGreaterThan() != null) {
            booleanBuilder.and(item.borrowPrice.goe(queryForm.getBorrowPriceGreaterThan()));
        }
        if(queryForm.getBorrowPriceLesserThan() != null) {
            booleanBuilder.and(item.borrowPrice.loe(queryForm.getBorrowPriceLesserThan()));
        }
        if(queryForm.isAvailable()) {
            booleanBuilder.and(item.isAvailable.eq(true));
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        queryFactory.selectFrom(item)
                .where(booleanBuilder)
                .orderBy(queryForm.isItemValueAsc() ?
                        item.itemValue.asc() : item.itemValue.desc())
                .orderBy(queryForm.isBorrowPriceAsc() ?
                        item.borrowPrice.asc() : item.borrowPrice.desc());
    }

    }



