package com.credi.fings.publics.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateObject {
    private Integer year;
    private Integer month;
    private Integer week;
    private Integer days;
    private Integer hour;
    private Integer minute;
    private Integer seconde;
    private Integer tierce;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Ajout des années avec abréviation 'a'
        if (year != null && year != 0) {
            sb.append(year).append(year == 1 ? " a " : " ans ");
        }

        // Ajout des mois avec abréviation 'm'
        if (month != null && month != 0) {
            sb.append(month).append(" m ");
        }

        // Ajout des semaines avec abréviation 'sem'
        if (week != null && week != 0) {
            sb.append(week).append(week == 1 ? " sem " : " sem ");
            // 'sem' reste inchangé au singulier et au pluriel
        }

        // Ajout des jours avec abréviation 'j'
        if (days != null && days != 0) {
            sb.append(days).append(" j ");
        }

        // Ajout des heures avec abréviation 'H'
        if (hour != null && hour != 0) {
            sb.append(hour).append(" H ");
        }

        // Ajout des minutes avec abréviation 'mn'
        if (minute != null && minute != 0) {
            sb.append(minute).append(" mn ");
        }

        // Toujours afficher les secondes avec abréviation 's'
        if (seconde != null) {
            sb.append(seconde).append(" s ");
        } else {
            sb.append("0 s ");
        }

        // Toujours afficher les tierces avec abréviation 't'
      /*  if (tierce != null) {
            sb.append(tierce).append(" t");
        } else {
            sb.append("0 t");
        }*/

        // Supprimer les espaces en fin de chaîne
        return sb.toString().trim();
    }
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSeconde() {
        return seconde;
    }

    public void setSeconde(Integer seconde) {
        this.seconde = seconde;
    }

    public Integer getTierce() {
        return tierce;
    }

    public void setTierce(Integer tierce) {
        this.tierce = tierce;
    }
    public static DateObject calculaterDifference(LocalDateTime startDate, LocalDateTime endDate) {
        DateObject dateObject = new DateObject();

        // Calcul des années
        long years = ChronoUnit.YEARS.between(startDate, endDate);
        startDate = startDate.plusYears(years);

        // Calcul des mois
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        startDate = startDate.plusMonths(months);

        // Calcul des semaines
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        startDate = startDate.plusWeeks(weeks);

        // Calcul des jours
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        startDate = startDate.plusDays(days);

        // Calcul des heures
        long hours = ChronoUnit.HOURS.between(startDate, endDate);
        startDate = startDate.plusHours(hours);

        // Calcul des minutes
        long minutes = ChronoUnit.MINUTES.between(startDate, endDate);
        startDate = startDate.plusMinutes(minutes);

        // Calcul des secondes
        long seconds = ChronoUnit.SECONDS.between(startDate, endDate);
        startDate = startDate.plusSeconds(seconds);

        // Calcul des tierces (millisecondes)
        long milliseconds = ChronoUnit.MILLIS.between(startDate, endDate);

        // Assignation des valeurs calculées aux champs de DateObject
        dateObject.setYear((int) years);
        dateObject.setMonth((int) months);
        dateObject.setWeek((int) weeks);
        dateObject.setDays((int) days);
        dateObject.setHour((int) hours);
        dateObject.setMinute((int) minutes);
        dateObject.setSeconde((int) seconds);
        dateObject.setTierce((int) milliseconds);

        return dateObject;
    }

    public static DateObject calculaterDifferenceEnHeur(LocalDateTime startDate, LocalDateTime endDate) {
        DateObject dateObject = new DateObject();

        // Calcul de la durée totale entre startDate et endDate
        Duration duration = Duration.between(startDate, endDate);

        if (duration.isNegative()) {
            // Si la durée est négative, inverser les dates
            duration = duration.negated();
        }

        long totalSeconds = duration.getSeconds();
        long totalMilliseconds = duration.toMillis() % 1000;

        // Calcul des heures totales, incluant les jours convertis en heures
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // Assignation des valeurs calculées aux champs de DateObject
        dateObject.setHour((int) hours);
        dateObject.setMinute((int) minutes);
        dateObject.setSeconde((int) seconds);
        dateObject.setTierce((int) totalMilliseconds);

        // Puisque les unités plus grandes que les heures sont ignorées,
        // les autres champs sont définis à zéro ou laissés null
        dateObject.setYear(0);
        dateObject.setMonth(0);
        dateObject.setWeek(0);
        dateObject.setDays(0);

        return dateObject;
    }
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    public static DateObject fromNaw(Date startDate){
        return calculaterDifference(convertToLocalDateTime(startDate),convertToLocalDateTime(new Date()));
    }
    public static DateObject fromNaw(Date startDate, Date endDate){
        return calculaterDifference(convertToLocalDateTime(startDate),convertToLocalDateTime(endDate));
    }

    public static int getYear(Date date) {
        // Convertir Date en LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Obtenir l'année
        return localDate.getYear();
    }
    public static int getDayOfMon(Date date) {
        // Convertir Date en LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Obtenir l'année
        return localDate.getDayOfMonth();
    }
    public static Month getMonth(Date date) {
        // Convertir Date en LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Obtenir l'année
        return localDate.getMonth();
    }
    public static Timestamp convertToTimestamp(LocalDateTime dateTime) {
        return Timestamp.valueOf(String.valueOf(dateTime));
    }

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public static Timestamp convertDateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
    public static Date convertTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    public static LocalDateTime convertTimestampToLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null; // Vous pouvez également lancer une exception selon votre besoin
        }
        Instant instant = timestamp.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    public static Date setToEndOfDay(LocalDate date) {
        // Obtenez l'heure à 23h59mn59s
        LocalTime endOfDay = LocalTime.of(23, 59, 59);
        // Combinez la date avec l'heure pour créer un LocalDateTime
        LocalDateTime endOfDayDateTime = LocalDateTime.of(date, endOfDay);
        // Convertissez LocalDateTime en Date
        return Date.from(endOfDayDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Définir le jour au premier du mois
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Récupérer la date du premier jour du mois
        return calendar.getTime();
    }
    public static Date lastDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Définir le jour au dernier jour du mois
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        // Récupérer la date du dernier jour du mois
        return calendar.getTime();
    }

    public static String dateHeur(String s){
        // SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date= null;

        try {
            ZonedDateTime zonedDateTime = parseFlexible(s);
            Instant instant = zonedDateTime.toInstant();
            date = Date.from(instant);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("dd MMM yyy 'à' HH:mm");
        if (date!=null){
            return simpleDateFormat2.format(date);
        }
        return s;
    }
    /**
     * Calculates the difference between the provided dateTime string and the current date-time.
     *
     * @param dateTime the date-time string in the format "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
     * @return a DateObject representing the difference
     * @throws IllegalArgumentException if the dateTime string is in an invalid format
     */

    public static DateObject fromToNaw(String dateTime) {
        try {
            ZonedDateTime startZdt = parseFlexible(dateTime);
            // Convert ZonedDateTime to LocalDateTime
            LocalDateTime start = startZdt.toLocalDateTime();
            LocalDateTime end   =  LocalDateTime.now();

            return calculaterDifferenceEnHeur(start, end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Impossible de parser la date. Format attendu : yyyy-MM-dd HH:mm:ss[.SSS], avec éventuellement T et/ou offset",
                    e
            );
        }
    }
    /**
     * Tente de parser n'importe quelle chaîne date/heure "assez standard".
     * Le renvoie sous forme de ZonedDateTime dans le fuseau local si pas d'offset détecté.
     */
    public static ZonedDateTime parseFlexible(String dateTimeString) {
        List<DateTimeFormatter> formatters = new ArrayList<>();

        // Formatter avec offset au format +HH:MM (ou Z) et fraction optionnelle
        formatters.add(new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendLiteral(' ').optionalEnd()
                .appendPattern("HH:mm:ss")
                .optionalStart()
                .appendLiteral('.')
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, false)
                .optionalEnd()
                .optionalStart()
                .appendOffset("+HH:MM", "Z")
                .optionalEnd()
                .toFormatter());

        // Formatter avec offset au format +HHMM (ou Z) et fraction optionnelle
        formatters.add(new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendLiteral(' ').optionalEnd()
                .appendPattern("HH:mm:ss")
                .optionalStart()
                .appendLiteral('.')
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, false)
                .optionalEnd()
                .optionalStart()
                .appendOffset("+HHMM", "Z")
                .optionalEnd()
                .toFormatter());

        // Formatter sans offset, avec fraction optionnelle
        formatters.add(new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart().appendLiteral('T').optionalEnd()
                .optionalStart().appendLiteral(' ').optionalEnd()
                .appendPattern("HH:mm:ss")
                .optionalStart()
                .appendLiteral('.')
                .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, false)
                .optionalEnd()
                .toFormatter());

        // Formatter ISO standard en dernier recours
        formatters.add(DateTimeFormatter.ISO_DATE_TIME);

        DateTimeParseException lastException = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                TemporalAccessor parsed = formatter.parse(dateTimeString);
                if (parsed.isSupported(ChronoField.OFFSET_SECONDS)) {
                    return ZonedDateTime.from(parsed);
                } else {
                    LocalDateTime ldt = LocalDateTime.from(parsed);
                    return ldt.atZone(ZoneId.systemDefault());
                }
            } catch (DateTimeParseException e) {
                lastException = e; // on mémorise la dernière exception
            }
        }
        // Si aucun formatter ne parvient à parser la chaîne, on lance une exception.
        throw new DateTimeParseException("Impossible de parser la date : " + dateTimeString, dateTimeString, 0, lastException);
    }

    public static Date convertZonedDateTimeToDate(ZonedDateTime zonedDateTime) {
        // Convertir ZonedDateTime en Instant, puis en Date
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
    public static DateObject fromToNaw(String dateTime, String dateFin) {
        try {
            ZonedDateTime startZdt = parseFlexible(dateTime);
            ZonedDateTime endZdt   = parseFlexible(dateFin);

            // Convert ZonedDateTime to LocalDateTime
            LocalDateTime start = startZdt.toLocalDateTime();
            LocalDateTime end   = endZdt.toLocalDateTime();

            return calculaterDifferenceEnHeur(start, end);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Impossible de parser la date. Format attendu : yyyy-MM-dd HH:mm:ss[.SSS], avec éventuellement T et/ou offset",
                    e
            );
        }
    }
    /**
     * Calcule la différence entre deux heures (time1 - time2) au format "HH:mm".
     * Par exemple, si time1 = "08:30" et time2 = "03:30", le résultat sera "05:00".
     *
     * @param time1 Heure au format "HH:mm"
     * @param time2 Heure au format "HH:mm"
     * @return      Différence (time1 - time2) au format "HH:mm"
     */
    public static String getTimeDifference(String time1, String time2) {
        // 1. Parsing de time1
        String[] parts1 = time1.split(":");
        int hour1 = Integer.parseInt(parts1[0]);
        int minute1 = Integer.parseInt(parts1[1]);

        // 2. Parsing de time2
        String[] parts2 = time2.split(":");
        int hour2 = Integer.parseInt(parts2[0]);
        int minute2 = Integer.parseInt(parts2[1]);

        // 3. Conversion en total de minutes depuis minuit
        int totalMinutes1 = hour1 * 60 + minute1;
        int totalMinutes2 = hour2 * 60 + minute2;

        // 4. Calcul de la différence en minutes
        int diff = totalMinutes1 - totalMinutes2;

        // 5. Gestion du signe (si vous voulez autoriser un résultat négatif)
        //    Ici, on suppose qu'on veut simplement un résultat absolu OU laisser tel quel
        //    Selon votre logique, vous pouvez forcer diff >= 0 si nécessaire.
        // if (diff < 0) {
        //     diff = -diff; // difference absolue
        // }

        // 6. Conversion de la différence en heures et minutes
        int diffHours = diff / 60;
        int diffMinutes = diff % 60;

        // 7. Formatage en "HH:mm" (avec zéro-padding si besoin)
        //    Si vous souhaitez qu'un résultat négatif affiche un signe "-", vous pouvez faire :
        //    return String.format("%s%02d:%02d", (diff < 0 ? "-" : ""), Math.abs(diffHours), Math.abs(diffMinutes));
        return String.format("%02d:%02d", diffHours, diffMinutes);
    }

    public static Date ajouterHeure(Date date, String heure) {
        // Format pour analyser la chaîne représentant l'heure.
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
        Date heureDate = null;
        try {
            heureDate = heureFormat.parse(heure);
        } catch (ParseException e) {
            return null;
        }

        // Initialiser un Calendar avec la date donnée.
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        // Initialiser un Calendar avec l'heure extraite.
        Calendar calHeure = Calendar.getInstance();
        calHeure.setTime(heureDate);

        // Remplacer l'heure et les minutes dans la date de base.
        calDate.set(Calendar.HOUR_OF_DAY, calHeure.get(Calendar.HOUR_OF_DAY));
        calDate.set(Calendar.MINUTE, calHeure.get(Calendar.MINUTE));
        // Optionnel : remettre les secondes et millisecondes à zéro.
        calDate.set(Calendar.SECOND, 0);
        calDate.set(Calendar.MILLISECOND, 0);

        return calDate.getTime();
    }
    public static Date addToDate(Date depart, int years, int months, int days, int hours, int minutes, int seconds) {
        if (depart == null) {
            throw new IllegalArgumentException("La date de départ ne doit pas être null");
        }

        // Conversion de Date en LocalDateTime
        LocalDateTime ldt = depart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Application directe des ajustements (les méthodes acceptent des valeurs négatives)
        ldt = ldt.plusYears(years)
                .plusMonths(months)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);

        // Conversion de LocalDateTime en Date
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
