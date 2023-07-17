<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <router-link to="/dashboard" class="navbar-brand">SmartHome</router-link>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link to="/dashboard" class="dropdown-item-text nav-link">Dashboard</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/devices" class="dropdown-item-text nav-link">Geräte</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/statistics" class="dropdown-item-text nav-link">Statistiken</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/tariffs" class="dropdown-item-text nav-link">Tarife</router-link>
                    </li>
                </ul>
                <p class="my-auto me-2">{{ time }} </p>
                <button class="btn btn-secondary-outline border-1" style="border: solid" @click="toggleDarkMode">
                    <i v-if="isDarkMode" class="bi bi-emoji-smile"></i>
                    <i v-else class="bi bi-emoji-sunglasses"></i>
                </button>
            </div>
        </div>
    </nav>
</template>

<script>
import {useCookies} from "vue3-cookies";
import axios from "axios";

export default {
    setup() {
        const {cookies} = useCookies();
        return {cookies};
    },

    data() {
        return {
            isDarkMode: true,
            time_timer: null,
            time: null,
        }
    },
    methods: {
        toggleDarkMode() {
            let element = document.getElementById("body")
            if (element.getAttribute("data-bs-theme") === "dark") {
                element.setAttribute("data-bs-theme", "white")
                this.isDarkMode = false;
                this.cookies.set("isDarkMode", "false")
            } else {
                element.setAttribute("data-bs-theme", "dark")
                this.isDarkMode = true
                this.cookies.set("isDarkMode", "true")
            }
        },
        loadTime() {
            axios.get("https://www.icecreamparty.de/api/time/").then(v => {
                this.time = this.formatDate(v.data.date) + ' ' + v.data.time + ' Uhr'
                this.sendToParent()
            })
        },
        startUpdateTimer() {
            this.time_timer = setInterval(this.loadTime, 1000)
        },
        stopUpdateTimer() {
            clearInterval(this.time_timer)
        },
        sendToParent() {
            this.$emit('data-updated', this.time)
        }
    },
    beforeMount() {
        this.loadTime()
        this.startUpdateTimer()
    },
    mounted() {
        this.isDarkMode = this.cookies.get("isDarkMode") === "true";
        if (!this.isDarkMode) {
            document.getElementById("body").setAttribute("data-bs-theme", "white")
        }
    },
    beforeUnmount() {
        this.stopUpdateTimer()
    }
}
</script>

<style scoped>

</style>