package com.prohacker.busapp.models

class Bus {
    //ints
    private var busId:Int = 0
    private var seats:Int = 0
    private var seatAvailable:Int = 0



    //Strings
    private var busModel: String? = null
    private var busColor: String? = null
    private var busCondition: String? = null



    fun getBusModel(): String? {
        return busModel
    }



    fun setBusModel(busModel: String?) {
        this.busModel = busModel
    }
}