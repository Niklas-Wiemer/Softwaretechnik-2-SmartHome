<template>
    <Navbar></Navbar>

    <div class="container">
        <div class="row my-3">
            <h1 class="col-12 text-center">Statistiken</h1>
            <hr class="col-6 mx-auto">
        </div>

        <div class="row my-3">
            <div class="col-4">
                <div class="card">

                    <div class="card-header">
                        <h5 class="text-center">Verbrauch letzten 7 Tage *</h5>
                    </div>

                    <div class="card-body">
                        <div class="row my-0">
                            <label for="statistic-consume-electricity" class="col-sm-auto col-form-label fw-bold">Stromverbrauch:</label>
                            <div class="col-sm-auto">
                                <input type="text" readonly class="form-control-plaintext"
                                       id="statistic-consume-electricity"
                                       :value=" consumeEnergy +' Watt'">
                            </div>
                        </div>
                    </div>

                    <div class="card-footer">
                        <small class="d-flex justify-content-end">* im Durchschnitt</small>
                    </div>

                </div>
            </div>
            <div class="col-8 d-flex align-items-center justify-content-center">
                <Line v-if="loaded" :data="consume_data" :options="options"/>
                <div class="" v-else>

                    <div class="alert alert-danger" role="alert">
                        <i class="bi bi-exclamation-triangle"></i>
                        Es konnten keine Daten geladen werden
                    </div>
                </div>
            </div>
        </div>

        <hr>

        <div class="row my-3">
            <div class="col-4">
                <div class="card">

                    <div class="card-header">
                        <h5 class="text-center">Produktion letzten 7 Tage *</h5>
                    </div>

                    <div class="card-body">
                        <div class="row my-0">
                            <label for="statistic-consume-electricity" class="col-sm-auto col-form-label fw-bold">Stromerzeugung:</label>
                            <div class="col-sm-auto">
                                <input type="text" readonly class="form-control-plaintext"
                                       id="statistic-consume-electricity"
                                       :value=" produceEnergy +' Watt'">
                            </div>
                        </div>
                    </div>

                    <div class="card-footer">
                        <small class="d-flex justify-content-end">* im Durchschnitt</small>
                    </div>

                </div>
            </div>
            <div class="col-8 d-flex justify-content-center align-items-center">
                <Line v-if="loaded" :data="produce_data" :options="options"/>
                <div v-else>
                    <div class="alert alert-danger" role="alert">
                        <i class="bi bi-exclamation-triangle"></i>
                        Es konnten keine Daten geladen werden
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js'

import {Line} from 'vue-chartjs'
import axios from "axios";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)

export default {
    name: "Statistics",
    components: {Navbar, Line},
    data() {
        return {
            loaded: false,
            current_date: null,
            updaterTimer: null,
            history: null,
            producer_history: null,
            consumeEnergy: 0,
            produceEnergy: 0,
            consume_data: {
                labels: [],
                datasets: [
                    {
                        label: 'Stromverbrauch',
                        backgroundColor: '#ffc100',
                        borderColor: 'rgb(224,255,0)',
                        data: []
                    }
                ],
            },
            produce_data: {
                labels: [],
                datasets: [
                    {
                        label: 'Stromproduktion',
                        backgroundColor: '#00ff13',
                        borderColor: 'rgb(0,185,8)',
                        data: []
                    }
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                elements: {
                    point: {
                        radius: 5
                    },
                }
            }
        }
    },
    methods: {
        loadDeviceHistory() {
            axios.get('http://localhost:' + this.port + '/deviceRunStats').then(data => {
                axios.get('http://localhost:' + this.port + '/deviceElectricityProductionPerHour').then(producer_data => {
                    axios.get("https://www.icecreamparty.de/api/time/").then(timeData => {
                        this.current_date = timeData.data.date
                        this.loadLabels()
                        this.producer_history = producer_data.data
                        this.history = data.data
                        this.calculate()
                        this.loaded = true
                    })
                })
            })
        },
        loadLabels() {
            for (let i = 6; i >= 0; i--) {
                this.consume_data.labels.push(this.formatDate(this.getDateLess(i)))
                this.produce_data.labels.push(this.formatDate(this.getDateLess(i)))
            }
        },
        calculate() {
            for (let i = 6; i >= 0; i--) {
                this.calculateAverageElectricityForDate(this.getDateLess(i))
                this.calculateAverageEnergyProduction(this.getDateLess(i))
            }
        },
        calculateAverageElectricityForDate(date) {
            let data = this.consume_data.datasets[0].data
            let elements = this.getElementsByDate(date)
            let amount = 0

            elements.forEach(element => {
                amount += element.electricityUsageForRun
                this.consumeEnergy += element.electricityUsageForRun
            })
            this.consumeEnergy = Math.round(this.consumeEnergy)
            data.push(amount)
        },
        calculateAverageEnergyProduction(date) {
            let data = this.produce_data.datasets[0].data
            let elements = this.getProducerElementsByDate(date)
            let amount = 0

            elements.forEach(element => {
                amount += element.electricityProduction
                this.produceEnergy += element.electricityProduction
            })
            this.produceEnergy = Math.round(this.produceEnergy)
            data.push(amount)
        },
        getElementsByDate(date) {
            return this.history.filter(element => element.date === date)
        },
        getProducerElementsByDate(date) {
            return this.producer_history.filter(element => element.date.includes(date))
        },
        getDateLess(less) {
            let date = new Date(this.current_date)
            date.setDate(date.getDate() - less)

            return date.getFullYear() + "-" +
                (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-" +
                (date.getDate() < 10 ? '0' + date.getDate() : date.getDate())
        }
    },
    beforeMount() {
        this.loadDeviceHistory()
    },
    unmounted() {
        clearInterval(this.updaterTimer)
    }
}
</script>

<style scoped>

</style>