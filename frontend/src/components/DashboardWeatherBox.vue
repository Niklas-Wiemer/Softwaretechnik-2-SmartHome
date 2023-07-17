<template>
    <div class=" col-lg-3 col-md-6 my-2">
        <div class="card h-100">
            <div class="card-body">
                <div class="row h-100 align-items-center">
                    <i class="col-auto fs-1 bi " :class="icon"></i>
                    <div class="col">
                        <div v-if="value !== undefined">
                            <h3 class="col m-0">{{ value }} {{ unit }}</h3>
                            <p class="col m-0">{{ name }}</p>
                        </div>
                        <div v-else>
                            <h3 class="col m-0">lädt...</h3>
                            <p class="col m-0">{{ name }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "DashboardWeatherBox",
    props: {
        name: {
            required: true,
            type: String
        },
        value: {
            required: true
        },
        unit: {
            required: true,
        },
        icon: {
            required: true,
            type: String
        }
    }
}
</script>

<style scoped>

</style>