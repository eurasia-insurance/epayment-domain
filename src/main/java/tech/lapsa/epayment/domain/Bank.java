package tech.lapsa.epayment.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.patterns.domain.HashCodePrime;

@Entity
@Table(name = "BANK")
@HashCodePrime(31)
public class Bank extends IntIdEntitySuperclass {

    private static final long serialVersionUID = 1L;

    // code

    @Basic
    @Column(name = "CODE", unique = true)
    protected String code;

    public String getCode() {
	return code;
    }

    // name
    @Basic
    @Column(name = "NAME")
    protected String name;

    public String getName() {
	return name;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "BANK_2_BIN", joinColumns = @JoinColumn(name = "BANK_ID"))
    @Column(name = "BIN", unique = true)
    protected Set<String> bins;

    public Collection<String> getBins() {
	return Collections.unmodifiableSet(bins);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.BANK_NAME.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(name) //
		.map(Localization.FIELD_NAME.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(name) //
		.map(Localization.FIELD_CODE.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

}
