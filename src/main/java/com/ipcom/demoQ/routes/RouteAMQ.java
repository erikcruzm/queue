package com.ipcom.demoQ.routes;

import javax.xml.bind.JAXBContext;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ipcom.demoQ.model.Item;
import com.ipcom.demoQ.validate.ValidateDataProcessor;
import com.ipcom.demoQ.validate.processor.BuildSQLProcessor;

@Component
public class RouteAMQ extends RouteBuilder {

	@Autowired
	ValidateDataProcessor validateDataProcessor;
	
	@Autowired
	BuildSQLProcessor buildSqlProcessor;

	@Override
	public void configure() throws Exception {

		// XML Data Format
		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(Item.class);
		xmlDataFormat.setContext(con);

		from("{{fromRoute}}")
				.log("Read Message from activemQ ${body}")
			.unmarshal(xmlDataFormat)
			.process(validateDataProcessor)
			.process(buildSqlProcessor)
				.log("JavaObject value is : ${body}")
				.log("Headers - - ${in.headers.nombre}")
			.to("activemq:sqlQueue");
		
//		from("activemq:sqlQueue")
//			.log("Read Message from sqlQueue ${body}")
//			.log("Read headers from sqlQueue ${headers}")
//			.choice()
//            .when(simple("${header.nombre} == 'ADD'"))
//                .log("Es agregar")
//            .when(simple("${header.nombre} == 'UPDATE'"))
//                .log("Es actualizar")
//            .otherwise()
//                .log("No se encontro operacion");
			
		
		
		
	}

}
