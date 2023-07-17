import {createApp} from 'vue'
import App from './App.vue'
import {createRouter, createWebHistory} from "vue-router";
import Dashboard from "./views/Dashboard.vue";
import Devices from "@/views/Devices.vue";
import Statistics from "@/views/Statistics.vue";
import Tariffs from "@/views/Tariffs.vue";

const routes = [
    {
        path: "/",
        redirect: "/dashboard"
    },
    {
        path: "/dashboard",
        component: Dashboard
    },
    {
        path: "/devices",
        component: Devices
    },
    {
        path: "/statistics",
        component: Statistics
    },
    {
        path: "/tariffs",
        component: Tariffs
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes,
    linkActiveClass: "active",

})

const app = createApp(App).use(router)


app.mixin({
    methods: {
        getDeviceUnit(device) {
            switch (device.deviceType) {
                case "SOLAR_PANEL":
                case "DISHWASHER":
                case "WASHING_MACHINE":
                case "AIR_CONDITIONER":
                    return "Watt"
                case "CAR":
                    return "%"
                default:
                    return ""
            }
        },
        formatDate(date) {
            let d = date.split("-")
            return this.date = d[2] + "." + d[1] + "." + d[0]
        },
    }
})

app.config.globalProperties.port = 8082

app.config.globalProperties.globalDevices = [
    {
        "ip": "192.168.1.101",
        "deviceName": "Solaranlage",
        "room": "Garten",
        "devicePurpose": "PRODUCER",
        "deviceType": "SOLAR_PANEL",
        "state": true,
        "electricityBasicUsage": 0,
        "runtime": 0
    },
    {
        "ip": "192.168.1.102",
        "deviceName": "Waschmaschine",
        "room": "Keller",
        "devicePurpose": "CONSUMER",
        "deviceType": "WASHING_MACHINE",
        "state": false,
        "electricityBasicUsage": 0,
        "runtime": 0
    },
    {
        "ip": "192.168.1.103",
        "deviceName": "Spühlmaschine",
        "room": "Küche",
        "devicePurpose": "CONSUMER",
        "deviceType": "DISHWASHER",
        "state": false,
        "electricityBasicUsage": 0,
        "runtime": 0
    },
    {
        "ip": "192.168.1.104",
        "deviceName": "Solaranlage",
        "room": "Balkon",
        "devicePurpose": "PRODUCER",
        "deviceType": "SOLAR_PANEL",
        "state": false,
        "electricityBasicUsage": 0,
        "runtime": 0
    },
    {
        "ip": "192.168.1.105",
        "deviceName": "Klimaanlage",
        "room": "Wohnzimmer",
        "devicePurpose": "CONSUMER",
        "deviceType": "AIR_CONDITIONER",
        "state": false,
        "electricityBasicUsage": 0,
        "runtime": 0
    },
    {
        "ip": "192.168.1.106",
        "deviceName": "Waschmaschine",
        "room": "Garage",
        "devicePurpose": "CONSUMER",
        "deviceType": "WASHING_MACHINE",
        "state": false,
        "electricityBasicUsage": 0,
        "runtime": 0
    }


]
app.mount('#app')
