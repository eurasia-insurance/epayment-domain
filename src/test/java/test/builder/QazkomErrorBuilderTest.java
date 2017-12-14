package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Test;

import tech.lapsa.epayment.domain.QazkomError;

public class QazkomErrorBuilderTest {

    @Test
    public void simple() {

	final String ERROR_PLAIN //
		= "<response order_id=\"740954651955272\">"
			+ "<error code=\"05\" time=\"2017-12-01 15:01:51\" type=\"auth\">Transaction declined</error>"
			+ "<session id=\"11429DD3085E5E2A92A64C93FD199C48\"/></response>";
	final String MESSAGE = "Transaction declined";
	final Instant CREATED = LocalDateTime.of(2017, 12, 1, 15, 1, 51).atZone(ZoneId.of("Asia/Almaty")).toInstant();
	final String ORDER_NUMBER = "740954651955272";
	final String CODE = "05";

	final QazkomError o = QazkomError.builder() //
		.fromRawXml(ERROR_PLAIN) //
		.build();

	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getMessage(), allOf(not(nullValue()), is(equalTo(MESSAGE))));
	assertThat(o.getCreated(), allOf(not(nullValue()), is(equalTo(CREATED))));
	assertThat(o.getOrderNumber(), allOf(not(nullValue()), is(equalTo(ORDER_NUMBER))));
	assertThat(o.getCode(), allOf(not(nullValue()), is(equalTo(CODE))));
    }
}
