package com.github.box.controller.tool;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/3/9 10:36
 */
public class WelcomeController implements Initializable {

    @FXML
    private Label cpuLabel;

    @FXML
    private Label memoryLabel;

    @FXML
    private Label processorsLabel;

    private static OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new TimerTask() {
            @Override
            public void run() {
                load();
            }
        });
    }

    private void load() {
        int availableProcessors = systemMXBean.getAvailableProcessors();
        double systemCpuLoad = systemMXBean.getProcessCpuLoad();
        long totalPhysicalMemorySize = systemMXBean.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = systemMXBean.getFreePhysicalMemorySize();
        double cpuLoadPercent = (int) (systemCpuLoad * 100);
        cpuLabel.setText(cpuLoadPercent + "%");
        double freePercent = freePhysicalMemorySize * 1.0 / totalPhysicalMemorySize;
        BigDecimal usedMemory = new BigDecimal((1 - freePercent) * 100);
        memoryLabel.setText(usedMemory.setScale(2, RoundingMode.HALF_EVEN) + "%");
        processorsLabel.setText(String.valueOf(availableProcessors));
    }

}
