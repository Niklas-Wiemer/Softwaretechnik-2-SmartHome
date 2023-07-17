<template>
    <navbar></navbar>
    <div class="container mt-3">
        <div>
            <h1 class="text-center">Geräte</h1>
            <hr>
        </div>


        <div v-if="devices !== null">
            <small>{{ devices.length }} {{ devices.length === 1 ? 'Gerät' : 'Geräte' }}</small>

            <div class="row">

                <DeviceList :devices="devices" @update:devices="loadDevices"></DeviceList>

                <div class="col-lg-3 col-md-4 col-sm-6 my-2">
                    <div class="card h-100 border-3" style="border-style: dashed"
                         data-bs-toggle="modal"
                         data-bs-target="#modal-add-device" @mouseenter="hover_add_device = true"
                         @mouseleave="hover_add_device = false"
                         @click="searchDevices"
                         :class="{'bg-dark-subtle': hover_add_device}">
                        <div class="card-body pointer d-flex justify-content-center align-items-center">
                            <div>
                                <div class="row text-center">
                                    <i class="bi bi-plus-lg fs-1"></i>
                                </div>
                                <div class="row justify-content-center">
                                    Gerät hinzufügen
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div v-else class="text-center">
            <p>Es konnte keine Verbindung mit dem Server aufgebaut werden.</p>
            <a href="#" @click="loadDevices">Erneut versuchen</a>
        </div>

    </div>

  <!-- add device menu modal -->
    <div class="modal fade" id="modal-add-device" tabindex="-1" aria-labelledby="modal-add-device-label"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="modal-add-device-label">Gerät hinzufügen</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Gerät</th>
                            <th scope="col">IP</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <template v-if="search_devices_data.length !== 0"
                                  v-for="(device, index) in search_devices_data">
                            <tr>
                                <th scope="row">{{ index + 1 }}</th>
                                <td>{{ device.deviceName }}</td>
                                <td>{{ device.ip }}</td>
                                <td>
                                    <button class="btn btn-success btn-sm" @click="addNewDevice(device)">
                                        Hinzufügen
                                    </button>
                                </td>
                            </tr>
                        </template>
                        </tbody>
                    </table>
                    <p v-if="search_devices_data.length === 0" class="text-center">Es konnte kein Gerät im Netzwerk
                        gefunden werden.</p>
                </div>
                <div class="modal-footer">
                    <small v-if="search_devices_data.length > 1">Es wurden {{ search_devices_data.length }} Geräte im
                        Netzwerk gefunden</small>
                    <small v-else-if="search_devices_data.length === 1">Es wurde 1 Gerät im Netzwerk gefunden</small>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import DevicesItem from "@/components/DevicesItem.vue";
import DeviceList from "@/components/DeviceList.vue";
import axios from "axios";

export default {
    name: "Devices",
    components: {DeviceList, DevicesItem, Navbar},
    data() {
        return {
            devices: null,
            currentDevice: {},
            hover_add_device: false,
            search_devices_data: [],
            error: null,
            errorTimer: null,
        }
    },
    mounted() {
        this.loadDevices()
        this.autoUpdate()
    },
    unmounted() {
        this.stopUpdate()
    },
    methods: {
        autoUpdate() {
            this.timer = setInterval(this.loadDevices, 1000 * 2)
        },
        stopUpdate() {
            clearInterval(this.timer)
        },
        addNewDevice(device) {
            axios.post('http://localhost:' + this.port + '/device', device).then(data => {
                if (data.status === 200) {
                    this.loadDevices().then(v => {
                        this.search_devices_data = this.search_devices_data.filter(d => d.ip !== device.ip)
                    })
                } else {
                    this.showError("Es ist ein Fehler aufgetreten.")
                }
            }).catch(() => {
                this.showError("Es ist ein Fehler aufgetreten.")
            })
        },
        loadDevices() {
            return axios.get('http://localhost:' + this.port + '/device').then(data => {
                this.devices = data.data
            })
        },
        searchDevices() {
            this.search_devices_data = this.globalDevices.filter(device => !this.include(device.ip));
        },
        include(ip) {
            let found = false
            this.devices.forEach(device => {
                if (device.ip === ip) {
                    found = true
                }
            })
            return found
        },
        showError(error) {
            if (this.errorTimer !== null) {
                clearInterval(this.errorTimer)
            }
            this.error = error
            this.errorTimer = setInterval(() => {
                this.error = null;
                clearInterval(this.errorTimer)
            }, 1000 * 2)
        },
    }
}
</script>

<style scoped>
.pointer {
    cursor: pointer;
}

</style>