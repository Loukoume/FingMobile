package com.credi.fing.pojo.err;

public class ErrorUtils {

    public static String buildErrorMessage(ApiErrorResponse errorResponse) {
        StringBuilder message = new StringBuilder();

        // Message principal
        if (errorResponse.getDefaultUserMessage() != null) {
            message.append("Erreur : ").append(errorResponse.getDefaultUserMessage()).append("\n");
        }

        // Code HTTP
        if (errorResponse.getHttpStatusCode() != null) {
            message.append("Code HTTP : ").append(errorResponse.getHttpStatusCode()).append("\n");
        }

        // Détails supplémentaires
        if (errorResponse.getDeveloperMessage() != null) {
            message.append("Détail technique : ").append(errorResponse.getDeveloperMessage()).append("\n");
        }

        // Boucle sur les erreurs individuelles
        if (errorResponse.getErrors() != null && !errorResponse.getErrors().isEmpty()) {
            message.append("Détails des erreurs :\n");
            for (ApiErrorDetail detail : errorResponse.getErrors()) {
                if (detail.getDefaultUserMessage() != null) {
                    message.append("• ").append(detail.getDefaultUserMessage());
                }
                if (detail.getParameterName() != null) {
                    message.append(" (paramètre : ").append(detail.getParameterName()).append(")");
                }
                message.append("\n");
            }
        }

        return message.toString().trim();
    }
}

