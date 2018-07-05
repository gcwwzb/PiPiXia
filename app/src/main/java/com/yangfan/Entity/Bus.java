package com.yangfan.Entity;

import java.util.List;

public class Bus {

    private String status;
    private String msg;
    // result包含的 result 数组，即若干信息
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    //Result 中每个车次对象
    public static class ResultBean {
        /**
         * startcity : 杭州
         * endcity : 上海
         * startstation : 客运中心站
         * endstation : 上海
         * starttime : 06:50
         * price : 68
         * bustype : 大高3
         * distance : 181
         */

        private String startcity;  //出发城市
        private String endcity;  //到达城市
        private String startstation;  //出发车站
        private String endstation;   //到达车站
        private String starttime;   //出发时间
        private String price;   //票价
        private String bustype;  //车型
        private String distance;   //距离

        public String getStartcity() {
            return startcity;
        }

        public void setStartcity(String startcity) {
            this.startcity = startcity;
        }

        public String getEndcity() {
            return endcity;
        }

        public void setEndcity(String endcity) {
            this.endcity = endcity;
        }

        public String getStartstation() {
            return startstation;
        }

        public void setStartstation(String startstation) {
            this.startstation = startstation;
        }

        public String getEndstation() {
            return endstation;
        }

        public void setEndstation(String endstation) {
            this.endstation = endstation;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBustype() {
            return bustype;
        }

        public void setBustype(String bustype) {
            this.bustype = bustype;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
