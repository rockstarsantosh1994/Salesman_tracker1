package com.example.xxovek.salesman_tracker1;

public class ConfigUrls {

    //private static final String ROOT_URL = "http://track.xxovek.com/src/";
    private static final String ROOT_URL = "http://192.168.0.108/22-1track/src/";
    private static final String USER_URL = "http://192.168.0.108/22-1track/public_html/salesandroid/";


    //Admin Url's................
    public static final String AUTHENTICATE_ADMIN_URL = ROOT_URL + "authenticateAdmin";
    public static final String ADD_ROUTE=ROOT_URL+"add_route";
    public static final String GET_ROUTE=ROOT_URL+"get_Route1";
    public static final String GET_SALESMAN=ROOT_URL+"get_Salesman1";
    public static final String GET_SHOPS=ROOT_URL+"get_Shop1";
    public static final String ADD_SALES_WORK=ROOT_URL+"add_sales_work";
    public static final String ADD_ROUTE_DETAILS=ROOT_URL+"addRouteDetails";
    public static final String ADD_SHOP_KEEPER_REGISTRATION=ROOT_URL+"shopKeeperRegistration";
    public static final String ADD_SALES_REGISTRATION=ROOT_URL+"salesRegistration";
    public static final String COUNTRY_URL=ROOT_URL+"countries";
    public static final String STATES_URL=ROOT_URL+"states";
    public static final String CITY_URL=ROOT_URL+"city";
    public static final String REMOVE_DETAILS =ROOT_URL+"removeDetails";
    public static final String FETCH_ROUTE_DETAILS =ROOT_URL+"fetch_route_detail";
    public static final String FETCH_WORK_DETAILS =ROOT_URL+"fetch_work_detail1";
    public static final String FETCH_SALE_INFO =ROOT_URL+"fetchSalesInfo1";
    public static final String FETCH_SHOP_INFO =ROOT_URL+"fetchShopsInfo";
    public static final String GET_STATES_BY_ID =ROOT_URL+"getStateBy_Id";
    public static final String GET_CITY_BY_ID =ROOT_URL+"getCity_byId";
    public static final String DISPLAY_SALESMAN =ROOT_URL+"display_salesman";
    public static final String DISPLAY_SHOPKEEPER =ROOT_URL+"display_shopkeeper";
    public static final String DISPLAY_ROUTE =ROOT_URL+"display_routes";
    public static final String DISPLAY_WORK_DETAILS =ROOT_URL+"display_work_details";
    public static final String DISPLAY_ROUTE_DETAILS =ROOT_URL+"display_route_details";
    public static final String TRACK_REPORT_DATA =ROOT_URL+"TrackReportData";
    public static final String FETCH_LIVE_TRACK_POSITION =ROOT_URL+"fetchLivetrackposition";

    //User URL's.................
    public static final String ADMIN_INFO = USER_URL + "admininfo";
    public static final String LOGIN_URL = USER_URL + "login";
    public static final String CLIENTS1 = USER_URL + "clients1";
    public static final String CLIENTS = USER_URL + "clients";
    public static final String CLIENTS_INFO = USER_URL + "clientsinfo";
    public static final String HISTORY_CLIENTS = USER_URL + "historyclients";
    public static final String DROPLATLONG = USER_URL + "droplatlong";

}
