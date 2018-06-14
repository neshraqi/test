package hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by renjer on 2017-02-18.
 */

public class RegisterBusRequest {
    //private String
    //chairnum[18]:16
    public final static int TYPE_MALE = 2;
    public final static int TYPE_FEMALE = 1;
    private List<String> chairs;
    private String personsCode;
    private String personsPersianFirstName1;
    private String personsPersianLastName;
    private String personsTypeAge;
    private String phonNumber;
    private String email;
    private String secCode;
    private String numberP;
    private String id;
    private String company;
    private String token;
    private String deparureDate;
    private String deparureTime;
    private String busType;
    private String source;
    private String destination;
    private String capacity;
    private String price;
    private String vip;
    private String searchId;
    private String img;
    private String daytime;
    private String daytimeText;
    //-----------------------------------------------

    public List<String> getChairs() {
        return chairs;
    }

    public String getPersonsCode() {
        return personsCode;
    }

    public String getPersonsPersianFirstName1() {
        return personsPersianFirstName1;
    }

    public String getPersonsPersianLastName() {
        return personsPersianLastName;
    }

    public String getPersonsTypeAge() {
        return personsTypeAge;
    }

    public String getPhonNumber() {
        return phonNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSecCode() {
        return secCode;
    }

    public String getNumberP() {
        return numberP;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getToken() {
        return token;
    }

    public String getDeparureDate() {
        return deparureDate;
    }

    public String getDeparureTime() {
        return deparureTime;
    }

    public String getBusType() {
        return busType;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getPrice() {
        return price;
    }

    public String getVip() {
        return vip;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getImg() {
        return img;
    }

    public String getDaytime() {
        return daytime;
    }

    public String getDaytimeText() {
        return daytimeText;
    }

    public static String getSEPARATOR() {
        return SEPARATOR;
    }

    //-----------------------------------------------
    public RegisterBusRequest(List<String> chairs, String personsCode, String personsPersianFirstName1, String personsPersianLastName, String personsTypeAge, String phonNumber, String email, String secCode, String numberP, String id, String company, String token, String deparureDate, String deparureTime, String busType, String source, String destination, String capacity, String price, String vip, String searchId, String img) {
        this.chairs = chairs;
        this.personsCode = personsCode;
        this.personsPersianFirstName1 = personsPersianFirstName1;
        this.personsPersianLastName = personsPersianLastName;
        this.personsTypeAge = personsTypeAge;
        this.phonNumber = phonNumber;
        this.email = email;
        this.secCode = secCode;
        this.numberP = numberP;
        this.id = id;
        this.company = company;
        this.token = token;
        this.deparureDate = deparureDate;
        this.deparureTime = deparureTime;
        this.busType = busType;
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.price = price;
        this.vip = vip;
        this.searchId = searchId;
        this.img = img;
        daytime = "";
        daytimeText = "";
    }

    //-----------------------------------------------
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("chairnum[]", toStringChairs());
        postDataParams.put("personscode", personsCode);
        postDataParams.put("personsPersianFirstName1", personsPersianFirstName1);
        postDataParams.put("personsPersianLastName", personsPersianLastName);
        postDataParams.put("personstypeage", personsTypeAge);
        postDataParams.put("phonnumber", phonNumber);
        postDataParams.put("email", email);
        postDataParams.put("seccode", secCode);
        postDataParams.put("numberp", numberP);
        postDataParams.put("id", id);
        postDataParams.put("company", company);
        postDataParams.put("token", token);
        postDataParams.put("deparureDate", deparureDate);
        postDataParams.put("deparureTime", deparureTime);
        postDataParams.put("busType", busType);
        postDataParams.put("source", source);
        postDataParams.put("destination", destination);
        postDataParams.put("capacity", capacity);
        postDataParams.put("price", price);
        postDataParams.put("vip", vip);
        postDataParams.put("searchId", searchId);
        postDataParams.put("img", img);
        postDataParams.put("daytime", daytime);
        postDataParams.put("daytimetext", daytimeText);
        return postDataParams;
    }
    //-----------------------------------------------
    private static final String SEPARATOR = ",";
    //-----------------------------------------------
    private String toStringChairs(){
        StringBuilder csvBuilder = new StringBuilder();
        for(String city : chairs){
            csvBuilder.append(city);
            csvBuilder.append(SEPARATOR);
        }
        String csv = csvBuilder.toString();
        //System.out.println(csv);
        //OUTPUT: Milan,London,New York,San Francisco,
        //Remove last comma
        csv = csv.substring(0, csv.length() - SEPARATOR.length());
        return csv;
    }
}
