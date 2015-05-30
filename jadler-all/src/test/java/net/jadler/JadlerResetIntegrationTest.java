/*
 * Copyright (c) 2012 - 2015 Jadler contributors
 * This program is made available under the terms of the MIT License.
 */
package net.jadler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static net.jadler.Jadler.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests that its possible to reset Jadler.
 */
public class JadlerResetIntegrationTest {

    @BeforeClass
    public static void start() {
        initJadler();
    }

    @AfterClass
    public static void close() {
        closeJadler();
    }

    @After
    public void reset() {
        resetJadler();
    }

    @Test
    public void test200() throws IOException {
        onRequest().respond().withStatus(200);
        assertStatus(200);
        verifyThatRequest().havingMethodEqualTo("GET").receivedOnce();
        verifyThatRequest().receivedOnce();
    }

    @Test
    public void test201() throws IOException {
        onRequest().respond().withStatus(201);
        assertStatus(201);
        verifyThatRequest().havingMethodEqualTo("GET").receivedOnce();
        verifyThatRequest().receivedOnce();
    }

    private void assertStatus(int expected) throws IOException {
        final HttpClient client = new HttpClient();
        final GetMethod method = new GetMethod("http://localhost:" + port() + "/");
        assertThat(client.executeMethod(method), is(expected));
        method.releaseConnection();
    }
}
