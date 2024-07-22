package frc.robot.lib.helpers;

public interface IDashboardProvider {
    void putDashboard();

    void putDashboardOnce();

    default void registerDashboard() {
        DashboardHelper.register(this);
    }
}