<template>
    <div class="col-lg-3 col-md-4 col-sm-6 my-2">
        <div class="card h-100 pointer user-select-none" @mouseenter="hover = true"
             @mouseleave="hover = false"
             :class="{'bg-dark-subtle': hover}" @click="$emit('onDeviceClick')">
            <div class="card-header d-flex align-items-center">
                <i class="col-auto me-2 fs-5 bi" :class="getDeviceIcon()"></i>
                <div class="col fw-bold text-truncate">{{ device.deviceName }}</div>
            </div>
            <div class="card-body">
                <p class="my-1"><b>Standort:</b> {{ device.room }}</p>
                <p class="my-1"><b>Status:</b> {{ device.state ? 'Aktiv' : 'Inaktiv' }}</p>

                <p v-if="device.devicePurpose === 'CONSUMER'" class="my-1"><b>Verbrauch:</b>
                    {{ device.state ? device.electricityBasicUsage : 0 }}
                    {{ getDeviceUnit(device) }}</p>
                <p v-else-if="device.devicePurpose ==='PRODUCER'" class="my-1"><b>Erzeugt:</b>

                    {{ device.state ? produce : 0}}
                    {{ getDeviceUnit(device) }}</p>
            </div>

        </div>
    </div>

</template>

<script>

import axios from "axios";

export default {
    name: "DevicesItem",
    props: {
        device: {
            required: true
        },
    },
    data() {
        return {
            hover: false,
            timer: null,
            produce: null
        }
    },
    methods: {
        onDeviceClick(device) {
            this.$emit.onDeviceClick(device)
        },
        getDeviceIcon() {
            switch (this.device.deviceType) {
                case "WASHING_MACHINE":
                    return "bi-music-player"
                case "SOLAR_PANEL":
                    return "bi-sun"
                case "CAR":
                    return "bi-ev-front"
                case "DISHWASHER":
                    return "bi-water"
                case "AIR_CONDITIONER":
                    return "bi-fan"
            }
        },

        loadProduce() {
            axios.get('http://localhost:' + this.port + '/deviceElectricityProductionPerHour').then(data => {
                this.produce = data.data.filter(v => v.device.ip === this.device.ip).sort(
                    (a, b) => b.id - a.id
                )[0].electricityProduction
                this.produce = Math.round(this.produce * 100) / 100
            }).catch(() => {
                console.log("Fehler beim laden der Produce Daten")
            })
        },
    },
    beforeMount() {
        if (this.device.devicePurpose === 'PRODUCER') {
            this.loadProduce()
            this.timer = setInterval(() => {
                this.loadProduce()
            }, 1000 * 5)
        }
    },
    unmounted() {
        clearInterval(this.timer)
    }
}
</script>

<style scoped>

.pointer {
    cursor: pointer
}

</style>