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

        // Détail technique
        if (errorResponse.getDeveloperMessage() != null) {
            message.append("Détail technique : ").append(errorResponse.getDeveloperMessage()).append("\n");
        }

        // Détails spécifiques
        if (errorResponse.getErrors() != null && !errorResponse.getErrors().isEmpty()) {
            message.append("Détails des erreurs :\n");
            for (ApiErrorDetail detail : errorResponse.getErrors()) {
                // Message utilisateur
                if (detail.getDefaultUserMessage() != null) {
                    message.append("• ").append(detail.getDefaultUserMessage());
                }

                // Paramètre concerné s'il existe
                if (detail.getParameterName() != null) {
                    message.append(" (paramètre : ").append(detail.getParameterName()).append(")");
                }

                // Vérification des args pour afficher des messages utiles
                if (detail.getArgs() != null && !detail.getArgs().isEmpty()) {
                    for (ApiErrorArgument arg : detail.getArgs()) {
                        String valueStr = arg.getValueAsString();
                        if (valueStr != null && !valueStr.equals("null")) {
                            message.append("\n    → Détail : ").append(valueStr);
                        }
                    }
                }

                message.append("\n");
            }
        }

        return message.toString().trim();
    }

}

