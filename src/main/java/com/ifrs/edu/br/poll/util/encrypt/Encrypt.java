package com.ifrs.edu.br.poll.util.encrypt;

import jakarta.persistence.AttributeConverter;

public class Encrypt implements AttributeConverter<String,String> {

    private final EncryptionUtil encryptionUtil;

    public Encrypt(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        return encryptionUtil.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionUtil.decrypt(s);
    }
}
