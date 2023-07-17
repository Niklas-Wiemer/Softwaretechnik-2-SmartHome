<template>
    <Navbar></Navbar>

    <div class="container">
        <div class="row my-3">
            <h1 class="col-12 text-center">Tarif Informationen</h1>
            <hr class="col-6 mx-auto">
        </div>

        <div class="col-4 card mx-auto" v-if="tarif">
            <div class="card-header">
                <h2 class="text-center">Aktueller Tarif</h2>
            </div>
            <div class="card-body">

                <div class="row my-0">
                    <label for="tarif-electricity-price" class="col-sm-auto col-form-label fw-bold">Strompreis:</label>
                    <div class="col-sm-8">
                        <input type="text" readonly class="form-control-plaintext" id="tarif-electricity-price"
                               :value="Math.round(tarif.electricityPrice * 100) / 100 + ' €/kWh'">
                    </div>
                </div>

            </div>
            <div class="card-footer">
                <small class="d-flex justify-content-end">
                    Stand: {{ formatDate(tarif.date) + ' ' + tarif.time.replace('.', ':') + ' Uhr' }}
                </small>
            </div>
        </div>

        <h5 v-else class="text-center">Es konnte kein Tarif geladen werden.</h5>

    </div>

</template>

<script>
import Navbar from "@/components/Navbar.vue";
import axios from "axios";

export default {
    name: "Tariffs",
    components: {Navbar},
    data() {
        return {
            tarif: null,
            updateTimer: null
        }
    },
    methods: {
        loadCurrentTarife() {
            axios.get('http://localhost:' + this.port + '/tarife').then(data => {
                this.tarif = data.data.sort((a, b) => a.id - b.id)[data.data.length - 1]
            })
        }
    },
    unmounted() {
        clearInterval(this.updateTimer)
    },
    beforeMount() {
        this.loadCurrentTarife()
        setInterval(this.loadCurrentTarife, 1000 )
    }
}
</script>

<style scoped>

</style>