package de.backend.smarthome_backend.simulation;

import de.backend.smarthome_backend.StaticTimeClass;
import de.backend.smarthome_backend.entity.*;
import de.backend.smarthome_backend.enums.DeviceType;
import de.backend.smarthome_backend.service.DeviceService;
import de.backend.smarthome_backend.service.DeviceServiceImpl;
import de.backend.smarthome_backend.service.TarifService;
import de.backend.smarthome_backend.service.TarifServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SimulationService {
    private final DeviceServiceImpl deviceService;
    private final TarifService tarifService;
    @Autowired
    public SimulationService(DeviceServiceImpl deviceService,TarifService tarifService) {
        this.deviceService = deviceService;
        this.tarifService = tarifService;
    }


    public void initDevice(long deviceID) {

        Device device = deviceService.getDeviceById(deviceID);
        switch (device.getDeviceType()) {
            case DISHWASHER -> {
                device.setRuntime((double) randInt(50, 90));
                device.setElectricityBasicUsage((double) (randInt(120, 240) * 10));
            }
            case WASHING_MACHINE -> {
                device.setRuntime((double) randInt(90, 180));
                device.setElectricityBasicUsage((double) (randInt(60, 90) * 10));
            }
            case CAR -> {
                device.setRuntime((double) randInt(30, 120));
                device.setElectricityBasicUsage((double) (randInt(22, 50) * 1000));
            }
            case SOLAR_PANEL -> {
                device.setRuntime((double) 0);
                device.setElectricityBasicProduction((double) (randInt(12, 23) * 100)/24);
            }
            case AIR_CONDITIONER -> {
                device.setRuntime(60.0);
                device.setElectricityBasicUsage((double) (randInt(7, 30) * 100));
            }
        }
        deviceService.updateDevice(device, deviceID);
    }

    public double d_Stromverbauch(long deviceId) {
        Device device = getDevice(deviceId);

        if (device.getDeviceType()== DeviceType.AIR_CONDITIONER) {
            double temp = getWetter().getTemp();
            if (28 < temp) {
                double result = scale(temp, 28, 35, device.getElectricityBasicUsage() * 0.5, device.getElectricityBasicUsage());
                if (result > device.getElectricityBasicUsage()) result = device.getElectricityBasicUsage();
                return result;
            } else
                return 0;
        }

        double h_laufzeit = device.getRuntime() / 60;
        return h_laufzeit * device.getElectricityBasicUsage();
    }

    public double d_StromProduktion(long deviceId, LocalTime localTime) {
        Device device = getDevice(deviceId);
        double pwr_prod;

        switch (getWetter().getWeatherName()) {
            case "Clear" ->
                    pwr_prod = randDouble(device.getElectricityBasicProduction() * 0.95, device.getElectricityBasicProduction());
            case "Clouds" ->
                    pwr_prod = randDouble(device.getElectricityBasicProduction() * 0.7, device.getElectricityBasicProduction() * 0.8);
            case "Mist" ->
                    pwr_prod = randDouble(device.getElectricityBasicProduction() * 0.25, device.getElectricityBasicProduction() * 0.4);
            default -> pwr_prod = 0;
        }

        if (localTime.getHour() > 22 || localTime.getHour() < 6) {
            pwr_prod = 0;
        } else if (localTime.getHour() >= 12 && localTime.getHour() <= 15) {
            pwr_prod *= randDouble(1.1, 1.15);
        } else {
            pwr_prod *= randDouble(0.95, 0.99);
        }

        return pwr_prod;
    }


    public static double randDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Device getDevice(long deviceId) {
        List<Device> liste = deviceService.fetchDeviceList();
        for (Device device : liste) {
            if (device.getDeviceID() == deviceId)
                return device;
        }
        return null;
    }

    public Wettermod getWetter() {
        RestTemplate restTem = new RestTemplate();
        ResponseEntity<Wettermod> response = restTem.getForEntity("https://www.icecreamparty.de/api/wetterdaten/now", Wettermod.class);
        return response.getBody();
    }

    public double scale(double in_val, double in_min, double in_max, double out_min, double out_max) {
        double percentage = (in_val - in_min) / (in_max - in_min);
        return (percentage * (out_max - out_min)) + out_min;
    }

    public DeviceRunStats createDeviceRunStats(Long deviceID){
        Double electricityConsume = d_Stromverbauch(deviceID);
        Double avgElectricity = electricityConsume * (deviceService.getDeviceById(deviceID).getRuntime() / 60);
        List<TarifEntity> tarif = tarifService.fetchTarifEntityList();
        return new DeviceRunStats(deviceService.getDeviceById(deviceID),
                StaticTimeClass.timeAndDate.date.toString(),electricityConsume,
                avgElectricity,calculateCost(deviceID),tarif.get(tarif.size() - 1).getElectricityPrice());
    }

    public List<DeviceElectricityProductionPerHour> createDeviceElectricityProductionPerHour(List<Long> deviceIDList){
        List<DeviceElectricityProductionPerHour> returnList = new ArrayList<>();
        for (Long deviceId:  deviceIDList) {
            returnList.add(new DeviceElectricityProductionPerHour(
                    deviceService.getDeviceById(deviceId)," " + StaticTimeClass.timeAndDate.date," " + StaticTimeClass.timeAndDate.time.getHour(),d_StromProduktion(deviceId,StaticTimeClass.timeAndDate.time)));
        }
        return returnList;
    }

    public Double calculateCost(Long deviceID){
        List<TarifEntity> tarif = tarifService.fetchTarifEntityList();
        tarif.sort(Comparator.comparing((TarifEntity t) -> LocalDate.parse(t.getDate()))
                        .thenComparing(t -> LocalTime.parse(t.getTime()))
                        .reversed());
        return (this.d_Stromverbauch(deviceID) / 1000) * tarif.get(0).getElectricityPrice();
    }

    public static double calculateGrundlast(){
        if( StaticTimeClass.timeAndDate == null){
            return 0;
        }
        LocalDate localDate = StaticTimeClass.timeAndDate.date;
        if(localDate.getMonthValue() < 4 || localDate.getMonthValue() > 8)
            return randInt(300, 500) * 1.15;
        else
            return randInt(300, 500);
    }
}

