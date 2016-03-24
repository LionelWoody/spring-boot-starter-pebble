package org.woodylab.boot.pebble;

import com.mitchellbosecke.pebble.error.LoaderException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.rules.ExpectedException.none;

public class PebbleTemplateLoaderTest {

    @Rule
    public ExpectedException thrown = none();

    private PebbleTemplateLoader pebbleTemplateLoader;

    @Before
    public void setUp() throws Exception {
        pebbleTemplateLoader = new PebbleTemplateLoader();

        final ResourceLoader resourceLoader = new StaticApplicationContext();
        pebbleTemplateLoader.setResourceLoader(resourceLoader);
    }

    @Test
    public void testGetReader() throws Exception {
        //Arrange
        final String resourceName = "templates/foo.html";

        //Act
        final Reader reader = pebbleTemplateLoader.getReader(resourceName);

        //Assert
        assertThat(reader, is(notNullValue()));
        assertThat(readerToString(reader), is(fileToString(resourceName)));
    }

    @Test
    public void testGetReaderWithPrefix() throws Exception {
        //Arrange
        final String fileName =  "templates/foo.html";

        final String resourceName = "foo.html";
        final String prefix = "templates/";
        pebbleTemplateLoader.setPrefix(prefix);

        //Act
        final Reader reader = pebbleTemplateLoader.getReader(resourceName);

        //Assert
        assertThat(reader, is(notNullValue()));
        assertThat(readerToString(reader), is(fileToString(fileName)));
    }

    @Test
    public void testGetReaderWithSuffix() throws Exception {
        //Arrange
        final String fileName =  "templates/foo.html";

        final String resourceName = "templates/foo";
        final String suffix = ".html";
        pebbleTemplateLoader.setSuffix(suffix);

        //Act
        final Reader reader = pebbleTemplateLoader.getReader(resourceName);

        //Assert
        assertThat(reader, is(notNullValue()));
        assertThat(readerToString(reader), is(fileToString(fileName)));
    }


    @Test
    public void testGetReaderFailsOnUnknownResource() throws Exception {
        //Arrange
        thrown.expect(LoaderException.class);

        final String resourceName = UUID.randomUUID().toString();

        //Act
        pebbleTemplateLoader.getReader(resourceName);

        //Assert
        fail(String.format("Expected a LoaderException, while the given resource '%s' exists", resourceName));
    }

    @Test
    public void testGetReaderWithPrefixAndSuffix() throws Exception {
        //Arrange
        final String fileName =  "templates/foo.html";

        final String resourceName = "templates/foo";
        final String prefix = "templates/";
        final String suffix = ".html";
        pebbleTemplateLoader.setPrefix(prefix);
        pebbleTemplateLoader.setSuffix(suffix);

        //Act
        final Reader reader = pebbleTemplateLoader.getReader(resourceName);

        //Assert
        assertThat(reader, is(notNullValue()));
        assertThat(readerToString(reader), is(fileToString(fileName)));
    }

    private String fileToString(final String fileName) {
        try {
            final URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
            final File file = new File(url.getPath());
            final Reader reader = new InputStreamReader(new FileInputStream(file));
            return readerToString(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String readerToString(final Reader reader) {
        try {
            try (BufferedReader buffer = new BufferedReader(reader)) {
                return buffer.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}