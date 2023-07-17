<template>
    <template v-for="device in devices">
        <DevicesItem :device="device" @onDeviceClick="onDeviceClick(device)"></DevicesItem>
    </template>


  <!-- Device information -->
    <div class="modal fade" id="modal-info-device" tabindex="-1" aria-labelledby="modal-info-device-label"
         aria-hidden="true">
        <div class="modal-dialog modal-md">
            <div class="modal-content">
                <div class="modal-header">
                    <div>
                        <h1 class="modal-title fs-5" id="modal-info-device-label">{{ currentDevice.deviceName }} </h1>
                        <small>ID: {{ currentDevice.deviceID }}, Type: {{ currentDevice.deviceType }}</small>
                    </div>

                    <div>
                        <button class="btn btn-outline-secondary mx-1" @click="editDevice=true"
                                :disabled="editDevice">
                            <i class="bi bi-pencil-square pointer"></i>
                        </button>
                    </div>


                </div>
                <div class="modal-body">
                    <form id="modal-info-device-edit-form">
                        <div class="row my-0">
                            <label for="modal-info-device-edit-name"
                                   class="col-sm-4 col-form-label fw-bold">Gerätename:</label>
                            <div class="col-sm-8 my-0 d-flex align-items-center">
                                <input type="text" :readonly="!editDevice"
                                       :class="{'form-control-plaintext': !editDevice, 'form-control form-control-sm':editDevice}"
                                       id="modal-info-device-edit-name"
                                       v-model="currentDevice.deviceName" required>
                            </div>
                        </div>
                        <div class="row my-0">
                            <label for="modal-info-device-edit-room"
                                   class="col-sm-4 col-form-label fw-bold">Standort:</label>
                            <div class="col-sm-8  d-flex align-items-center">
                                <input type="text" :readonly="!editDevice"
                                       :class="{'form-control-plaintext': !editDevice, 'form-control form-control-sm':editDevice}"
                                       id="modal-info-device-edit-room"
                                       v-model="currentDevice.room" required>
                            </div>
                        </div>
                    </form>

                    <div class="row my-0">
                        <label for="modal-info-device-edit-room"
                               class="col-sm-4 col-form-label fw-bold">Status:</label>
                        <div class="col-sm-8">
                            <input type="text" readonly class="form-control-plaintext" id="modal-info-device-edit-room"
                                   :value="currentDevice.state ?  'Aktiv' : 'Inaktiv'">
                        </div>
                    </div>
                    <div class="row my-0" v-if="currentDevice.devicePurpose === 'PRODUCER'">
                        <label for="modal-info-device-edit-room"
                               class="col-sm-4 col-form-label fw-bold">Erzeugt:</label>
                        <div class="col-sm-8">
                            <input type="text" readonly class="form-control-plaintext" id="modal-info-device-edit-room"
                                   :value="currentDevice.electricityBasicUsage + ' ' + getDeviceUnit(currentDevice)">
                        </div>
                    </div>

                    <div class="row my-0" v-if="currentDevice.devicePurpose === 'CONSUMER'">
                        <label for="modal-info-device-edit-room"
                               class="col-sm-4 col-form-label fw-bold">Verbrauch:</label>
                        <div class="col-sm-8">
                            <input type="text" readonly class="form-control-plaintext" id="modal-info-device-edit-room"
                                   :value="currentDevice.electricityBasicUsage + ' ' + getDeviceUnit(currentDevice)">
                        </div>
                    </div>

                    <div class="alert alert-danger d-flex align-items-center justify-content-between" role="alert"
                         v-if="error !== null">
                        <div>
                            <i class="bi bi-exclamation-triangle"></i>
                            {{ error }}
                        </div>
                        <button type="button" class="btn-close" @click="error = null" aria-label="Close"></button>
                    </div>

                </div>
                <div class="modal-footer d-flex justify-content-between align-items-start">
                    <div class="col-12 d-flex justify-content-between">
                        <button class="btn btn-secondary" @click="editDevice=false; error=null" v-if="editDevice"><i
                                class="bi bi-x-lg"></i>
                            Abbrechen
                        </button>
                        <button class="btn btn-success" form="modal-info-device-edit-form" type="submit"
                                v-if="editDevice" @click="onDeviceChange">
                            <i class="bi bi-clipboard2-check"></i>
                            Speichern
                        </button>
                    </div>

                    <button class="btn btn-info mb-2 d-flex align-items-center"
                            v-if="!editDevice && currentDevice.devicePurpose === 'CONSUMER'"
                            data-bs-target="#modal-plan-device"
                            data-bs-toggle="modal">
                        <i class="bi bi-calendar3 me-1"></i>
                        Programm planen
                    </button>

                    <button v-if="!editDevice && currentDevice.status === 'Programm geplant'"
                            class="btn btn-secondary"> <!-- ToDo Programm geplant -->
                        <i class="bi bi-x-lg"></i>
                        Programm abbrechen
                    </button>

                    <button class="btn btn-primary mb-2" data-bs-target="#modal-device-history"
                            data-bs-toggle="modal"
                            v-if="!editDevice && currentDevice.devicePurpose !== 'PRODUCER'" @click="loadDeviceHistory">
                        <i class="bi bi-clock me-1"></i>
                        Verlauf
                    </button>

                    <div v-if="!editDevice">
                        <button class="btn btn-success my-0" v-if="!currentDevice.state" @click="activateDevice">
                            <i class="bi bi-caret-right-fill"></i> Einschalten
                        </button>
                        <button class="btn btn-danger my-0" v-else @click="deactivateDevice">
                            <i class="bi bi-caret-right-fill"></i> Ausschalten
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

  <!-- Device plan program -->
    <div class="modal fade" id="modal-plan-device" tabindex="-1" aria-labelledby="modal-plan-device-label"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="modal-plan-device-label">Programm planen</h1>
                </div>
                <div class="modal-body">
                    <form id="modal-plan-device-form" @submit="onBeginProgram">
                        <div class="row my-2">
                            <label for="modal-plan-device-start" class="col-sm-3 col-form-label">Start: *</label>
                            <div class="col-sm-auto">
                                <input type="date" class="form-control"
                                       id="modal-plan-device-end" required v-model="timeBegin_date">
                            </div>
                            <div class="col-sm-auto">
                                <input type="time" class="form-control"
                                       id="modal-plan-device-start" required v-model="timeBegin_time">
                            </div>
                        </div>
                        <div class="row my-2">
                            <label for="modal-plan-device-end" class="col-sm-3 col-form-label">Ende: *</label>
                            <div class="col-sm-auto">
                                <input type="date" class="form-control"
                                       id="modal-plan-device-end" required v-model="timeEnd_date">
                            </div>
                            <div class="col-sm-auto">
                                <input type="time" class="form-control"
                                       id="modal-plan-device-end" required v-model="timeEnd_time">
                            </div>
                        </div>

                        <div class="row my-2">
                            <label for="modal-plan-device-wish" class="col-sm-4 col-form-label">Wunschpreis: *</label>
                            <div class="input-group col-sm-auto d-flex align-items-center">
                                <input type="number" class="form-control" step="0.01" placeholder="0.4"
                                       id="modal-plan-device-wish" required v-model="desiredPrice">
                                <span class="input-group-text" id="basic-addon1">
                                    <i class="bi bi-currency-euro"></i>
                                </span>
                            </div>
                        </div>

                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="modal-plan-device-wish-check"
                                   :checked="desiredPriceCheck"
                                   @click="desiredPriceCheck = !desiredPriceCheck">
                            <label class="form-check-label user-select-none" for="modal-plan-device-wish-check">
                                Preisüberschreitung
                            </label>
                        </div>

                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="modal-plan-device-stop-check"
                                   :checked="shouldStopDevice"
                                   @click="shouldStopDevice = !shouldStopDevice">
                            <label class="form-check-label user-select-none" for="modal-plan-device-stop-check">
                                Programm beenden
                            </label>
                        </div>
                    </form>

                    <small class="d-flex justify-content-end">* Pflichtfelder</small>

                </div>
                <div class="modal-footer d-flex justify-content-between">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-lg"></i>
                        Abbrechen
                    </button>
                    <button class="btn btn-success" form="modal-plan-device-form" type="submit">
                        <i class="bi bi-caret-right-fill"></i> Programm starten
                    </button>
                </div>
            </div>
        </div>
    </div>

  <!-- Device history -->
    <div class="modal fade" id="modal-device-history" tabindex="-1" aria-labelledby="modal-device-history-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="modal-device-history-label">Verlauf</h1>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Datum</th>
                            <th scope="col">Stromverbrauch</th>
                            <th scope="col">Kosten</th>
                            <th scope="col">Tarifpreis</th>
                        </tr>
                        </thead>
                        <tbody>
                        <template v-if="device_history !== null" v-for="(history, index) in device_history">
                            <tr>
                                <th scope="row">{{ index + 1 }}</th>
                                <td>{{ formatDate(history.date) }}</td>
                                <td>{{ Math.round(history.electricityUsageForRun) + ' W' }}</td>
                                <td>{{ Math.round(history.costOfTheRun * 100) / 100 }} €</td>
                                <td>{{ Math.round(history.tarifPrice * 100) / 100 + ' €' }}</td>
                            </tr>
                        </template>
                        </tbody>
                    </table>
                    <div v-if="device_history === null">
                        <p v-if="!error" class="text-center">Lade Daten...</p>
                        <div class="alert alert-danger d-flex align-items-center justify-content-between" role="alert"
                             v-if="error !== null">
                            <div>
                                <i class="bi bi-exclamation-triangle"></i>
                                {{ error }}
                            </div>
                        </div>
                    </div>

                    <p v-else-if="device_history.length === 0" class="text-center">Das wurde noch nie
                        eingeschaltet.</p>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-lg"></i>
                        Schließen
                    </button>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import DevicesItem from "@/components/DevicesItem.vue";
import axios from "axios";

export default {
    name: "DeviceList",
    components: {DevicesItem},
    props: {
        devices: {
            required: true
        }
    },
    data() {
        return {
            currentDevice: {},
            editDevice: false,
            error: null,
            timeBegin_date: null,
            timeBegin_time: null,
            timeEnd_date: null,
            timeEnd_time: null,
            desiredPrice: null,
            device_history: null,
            desiredPriceCheck: false,
            shouldStopDevice: false
        }
    },
    emits: ['update:devices'],
    methods: {
        onDeviceClick(device) {
            this.editDevice = false
            this.error = null
            this.currentDevice = device

            this.timeBegin_date = null
            this.timeBegin_time = null
            this.timeEnd_date = null
            this.timeEnd_time = null
            this.desiredPrice = null
            this.desiredPriceCheck = false
            this.shouldStopDevice = false

            this.device_history = null

            const modal = bootstrap.Modal.getOrCreateInstance('#modal-info-device', {})
            modal.show()
        },
        onBeginProgram() {
            axios.post('http://localhost:' + this.port + '/schedule', {
                device: this.currentDevice,
                runtime: this.currentDevice.runtime,
                scheduleBeginn: this.timeBegin_date + 'T' + this.timeBegin_time + ':00D',
                scheduleEnd: this.timeEnd_date + 'T' + this.timeEnd_time + ':00D',
                desiredPrice: this.desiredPrice,
                canExceedPrice: this.desiredPriceCheck,
                shouldStopDevice: this.shouldStopDevice
            }).then(data => {
                if (data.status === 200) {
                    const modal = bootstrap.Modal.getOrCreateInstance('#modal-plan-device', {})
                    modal.hide()
                } else {
                    this.error = "Das Programm konnte nicht geplant werden."
                }
            }).catch(() => {
                this.error = "Es konnte keine Verbindung zum Server aufgebaut werden."
            })
        },
        onDeviceChange() {
            axios.put('http://localhost:' + this.port + '/device/' + this.currentDevice.deviceID, this.currentDevice).then(data => {
                this.$emit('update:devices')
            })
        },
        activateDevice() {
            axios.get('http://localhost:' + this.port + '/device/checkDeviceOn/' + this.currentDevice.deviceID, {
                params: {
                    device_id: this.currentDevice.deviceId,
                    runtime: this.currentDevice.runtime
                }
            }).then(data => {
                if (data.status === 200) {
                    this.$emit('update:devices')
                    const modal = bootstrap.Modal.getOrCreateInstance('#modal-info-device', {})
                    modal.hide()
                } else {
                    this.error = "Das Gerät konnt nicht eingeschaltet werden."
                }
            }).catch(() => {
                this.error = "Beim Verbingungsaufbau ist ein Fehler aufgetreten."
            })
        },
        deactivateDevice() {
            // ToDo anfragen
            axios.post('http://localhost:' + this.port + '/device/turnDeviceOff/' + this.currentDevice.deviceID).then(data => {
                this.$emit('update:devices')
                const modal = bootstrap.Modal.getOrCreateInstance('#modal-info-device', {})
                modal.hide()
            })
        },
        loadDeviceHistory() {
            axios.get('http://localhost:' + this.port + '/deviceRunStats').then(data => {
                if (data.status === 200) {
                    this.device_history = data.data.sort((a, b) => b.id - a.id)
                } else {
                    this.error = 'Es ist ein Fehler aufgetreten.'
                }
            })
                .catch(e => {
                    console.log(e)
                    this.error = 'Es konnte keine Verbindung zum Server hergestellt werden.'
                })
        }
    },
    setup(props, {emit}) {
        const updateDevices = () => {
            emit('update:devices')
        }
        return {updateDevices}
    }
}
</script>

<style scoped>

</style>