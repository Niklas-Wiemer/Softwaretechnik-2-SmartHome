<template>
    <Navbar @data-updated="updateReceiveData"></Navbar>

    <div class="container mt-3">
        <div>
            <div class="row">
                <h1 class="col text-center my-auto"> {{ time }} </h1>
            </div>
            <hr>
        </div>

        <!-- Weather -->
        <div class="col">
            <div class="row">
                <p class="col-auto me-auto fw-bold">Wetter Informationen</p>
            </div>
            <div class="row justify-content-sm-start justify-content-center">

                <!-- request temperature -->
                <DashboardWeatherBox icon="bi-thermometer-half" :value="weather.temp" :unit="'°C'"
                                     name="Außentemperatur"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-thermometer" :value="weather.tempMin" :unit="'°C'"
                                     name="Niedrigste Temperatur"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-thermometer-high" :value="weather.tempMax" :unit="'°C'"
                                     name="Höchste Temperatur"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-thermometer-low" :value="weather.feelsLike" :unit="'°C'"
                                     name="Gefühlte Temperatur"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-speedometer2" :value="weather.pressure" :unit="'hPa'"
                                     name="Luftdruck"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-droplet" :value="weather.humidity" :unit="'%'"
                                     name="Luftfeuchtigkeit"></DashboardWeatherBox>

                <DashboardWeatherBox :icon="getWeatherIcon()" :value="weather.temp === undefined ? undefined : ''"
                                     :unit="getWeatherName()"
                                     name="Wetter"></DashboardWeatherBox>

                <DashboardWeatherBox icon="bi-wind" :value="weather.windSpeed" :unit="'km/h'"
                                     name="Windgeschwindigkeit"></DashboardWeatherBox>

            </div>
        </div>

    </div>
</template>

<script>

import Navbar from "@/components/Navbar.vue";
import DashboardWeatherBox from "@/components/DashboardWeatherBox.vue";
import axios from "axios";
import DevicesItem from "@/components/DevicesItem.vue";
import DeviceList from "@/components/DeviceList.vue";

export default {
    components: {DeviceList, DevicesItem, DashboardWeatherBox, Navbar},
    data() {
        return {
            weather: [],
            weather_timer: null,
            time_timer: null,
            time: null,
            timer: null,
        }
    },
    methods: {
        getWeatherName() {
            switch (this.weather.weatherName) {
                case "Clear":
                    return 'Sonne'
                case "Clouds":
                    return 'Wolken'
                case "Drizzle":
                    return 'Nieselregen'
                case "Rain":
                    return 'Regen'
                case "Snow":
                    return 'Schnee'
                case "Thunderstorm":
                    return 'Gewitter'
                case "Mist":
                    return 'Nebel'
            }
        },
        getWeatherIcon() {
            switch (this.weather.weatherName) {
                case "Clear":
                    return 'bi-brightness-high'
                case "Clouds":
                    return 'bi-cloudy'
                case "Drizzle":
                    return 'bi-cloud-drizzle'
                case "Rain":
                    return 'bi-cloud-rain'
                case "Snow":
                    return 'bi-cloud-snow'
                case "Thunderstorm":
                    return 'bi-cloud-lightning-rain'
                case "Mist":
                    return "bi-cloud-fog2"
                default:
                    return "bi-arrow-clockwise"
            }
        },
        loadWeatherData() {
            axios.get('https://www.icecreamparty.de/api/wetterdaten/now')
                .then(result => this.weather = result.data)
                .catch(() => this.weather = [])
        },
        loadTime() {
            axios.get("https://www.icecreamparty.de/api/time/").then(v => {
                this.date = this.formatDate(v.data.date);
                this.time = v.data.time;
            })
        },
        startUpdateTimer() {
            // Update weather every 5 seconds
            this.weather_timer = setInterval(this.loadWeatherData, 1000 * 5)
            // this.time_timer = setInterval(this.loadTime, 1000)
        },
        stopUpdateTimer() {
            clearInterval(this.weather_timer)
            clearInterval(this.time_timer)
        },
        updateReceiveData(time) {
            this.time = time
        }
    },
    created() {
        this.loadWeatherData()
        this.startUpdateTimer()
        this.loadTime()
    },
    beforeUnmount() {
        this.stopUpdateTimer()
    }
}

</script>

<style scoped>
</style>