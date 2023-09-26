package com.williamramos.facade;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import static com.google.api.services.calendar.CalendarScopes.CALENDAR_EVENTS;

public class ConectCalendarFacade {
    private static final String APPLICATION_NAME = "Agenda";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR, CALENDAR_EVENTS, TasksScopes.TASKS);

    private static final String CREDENTIALS_FILE_PATH = "/security.json";
    public static final String AQUIVO_DE_CREDENCIAIS_NAO_ENCONTRADO = "Aquivo de credenciais nao Encontrado";
    public static final String FALHA_AO_TENTAR_SE_CONECTAR_AO_GOOGLE = "Falha ao tentar se conectar ao Google";
    public static final String ERRO_DESCONHECIDO = "Erro Desconhecido";



    private static Credential getCredentials(final NetHttpTransport httpTransport) {
        try {
            InputStream inputStream = ConectCalendarFacade.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            assert inputStream != null;
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline").build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new RuntimeException(AQUIVO_DE_CREDENCIAIS_NAO_ENCONTRADO);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(FALHA_AO_TENTAR_SE_CONECTAR_AO_GOOGLE);
        } catch (Exception e) {
            throw new RuntimeException(ERRO_DESCONHECIDO);
        }
    }


    public static Calendar getCalendarService() {
        try {
            final NetHttpTransport http = GoogleNetHttpTransport.newTrustedTransport();
            return new Calendar.Builder(http, JSON_FACTORY, getCredentials(http)).setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Tasks getTasksService(){
        try {
            InputStream inputStream = ConectCalendarFacade.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            HttpTransport httpTransport = com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport();
            return new Tasks.Builder(httpTransport, JSON_FACTORY, getCredentials((NetHttpTransport) httpTransport)).setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
