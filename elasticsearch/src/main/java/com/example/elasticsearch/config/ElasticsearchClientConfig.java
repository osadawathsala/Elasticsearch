package com.example.elasticsearch.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

/**
 * @author Osada
 * @Date 1/20/2024
 * connection to the Elasticsearch
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.elasticsearch")
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {

    String certificateBase64;

    /**
     using ElasticsearchConfiguration,we can inject the following beans into other Spring components
     ElasticsearchOperations;
     ElasticsearchClient;
     RestClient;
     using ReactiveElasticsearchConfiguration we can inject
     ReactiveElasticsearchOperations operations;
     ReactiveElasticsearchClient elasticsearchClient;
     RestClient restClient;

     */
    @Override
    public ClientConfiguration clientConfiguration() {

        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

//        try {
//            return ClientConfiguration.builder()
//                    .connectedTo("localhost:9200")
//                    .usingSsl(getSSLContext())
//                    .withBasicAuth("elastic", "test1234")
//                    .build();
//        } catch (CertificateException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        } catch (KeyManagementException e) {
//            throw new RuntimeException(e);
//        }

    }
    private SSLContext getSSLContext() throws
            CertificateException,
            IOException, NoSuchAlgorithmException,
            KeyStoreException,
            KeyManagementException
    {
        byte[] decode = Base64.getDecoder().decode(certificateBase64);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        try (InputStream certificateInputStream = new ByteArrayInputStream(decode)) {
            ca = cf.generateCertificate(certificateInputStream);
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context;
    }
}
