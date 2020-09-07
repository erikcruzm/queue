package com.ipcom.demoQ.validate.processor;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.ipcom.demoQ.model.Item;


@Component
public class BuildSQLProcessor implements org.apache.camel.Processor {
	
    @Override
    public void process(Exchange exchange) throws Exception {

        Item item = (Item) exchange.getIn().getBody();

        String tableName ="ITEMS";
        StringBuilder query = new StringBuilder();

        if(item.getOperationType().equals("ADD")){
            query.append("INSERT INTO "+tableName+ " (SKU, ITEM_DESCRIPTION, PRICE) VALUES ('");
            query.append(item.getSku()+"','"+item.getItemDescription()+"'," + item.getPrice() +");");

        }else if(item.getOperationType().equals("UPDATE")){
            query.append("UPDATE "+tableName+" SET PRICE =");
            query.append(item.getPrice()+" where SKU = '"+item.getSku()+"'");

        }else if(item.getOperationType().equals("DELETE")){
            query.append("DELETE FROM " + tableName + " where SKU = '"+item.getSku()+"'");
        }

        exchange.getIn().setBody(query.toString());
        exchange.getIn().setHeader("nombre", item.getOperationType());
    }
}