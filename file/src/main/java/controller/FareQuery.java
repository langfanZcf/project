package controller;

import service.StationFareQuery;

import static service.ParameterParseTask.doInBackground;

public class FareQuery {


    public static void main (String args[]){
        //程序启动即加载
        doInBackground();
        //定义入参
        String start="0241";
        String end="0245";
        StationFareQuery  stationFareQuery =new StationFareQuery();
        stationFareQuery.FareQueryMoney(start,end);

    }

}
