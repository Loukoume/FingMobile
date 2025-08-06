package com.credi.fing.pojo.err;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ErrorUtils {
    public static String buildErrorMessage(ApiErrorResponse errorResponse) {
        return errorResponse.toString();
    }

    public static String buildErrorMessage2(ApiErrorResponse errorResponse) {
        StringBuilder message = new StringBuilder();

        // Horodatage
        if (errorResponse.getTimestamp() != null) {
            message.append("Horodatage : ")
                    .append(Instant.ofEpochMilli(errorResponse.getTimestamp())
                            .atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .append("\n");
        }

        // Statut HTTP
        if (errorResponse.getStatus() != null) {
            message.append("Statut HTTP : ")
                    .append(errorResponse.getStatus())
                    .append("\n");
        }

        // Type d'erreur (Internal Server Error, Bad Request…)
        if (errorResponse.getError() != null) {
            message.append("Type d'erreur : ")
                    .append(errorResponse.getError())
                    .append("\n");
        }

        // Message général renvoyé par le serveur (inclut parfois la nested exception)
        if (errorResponse.getMessage() != null) {
            message.append("Message serveur : ")
                    .append(errorResponse.getMessage())
                    .append("\n");
        }

        // Chemin de la requête
        if (errorResponse.getPath() != null) {
            message.append("Chemin : ")
                    .append(errorResponse.getPath())
                    .append("\n");
        }

        // Message utilisateur par défaut
        if (errorResponse.getDefaultUserMessage() != null) {
            message.append("Erreur : ")
                    .append(errorResponse.getDefaultUserMessage())
                    .append("\n");
        }

        // Détail technique (developerMessage)
        if (errorResponse.getDeveloperMessage() != null) {
            message.append("Détail technique : ")
                    .append(errorResponse.getDeveloperMessage())
                    .append("\n");
        }

        // Détails spécifiques
        if (errorResponse.getErrors() != null && !errorResponse.getErrors().isEmpty()) {
            message.append("Détails des erreurs :\n");
            for (ApiErrorDetail detail : errorResponse.getErrors()) {
                // Message utilisateur
                if (detail.getDefaultUserMessage() != null) {
                    message.append("• ")
                            .append(detail.getDefaultUserMessage());
                }
                // Paramètre concerné
                if (detail.getParameterName() != null) {
                    message.append(" (paramètre : ")
                            .append(detail.getParameterName())
                            .append(")");
                }
                message.append("\n");
                // Arguments d'erreur
                if (detail.getArgs() != null && !detail.getArgs().isEmpty()) {
                    for (ApiErrorArgument arg : detail.getArgs()) {
                        String valueStr = arg.getValueAsString();
                        if (valueStr != null && !"null".equals(valueStr)) {
                            message.append("    → Détail : ")
                                    .append(valueStr)
                                    .append("\n");
                        }
                    }
                }
            }
        }

        return message.toString().trim();
    }

}

