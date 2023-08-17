package hu.tyufi.process;

import java.util.Arrays;

public class ProcessApiShowcase {

    public static void main(String[] args) {
        ProcessHandle.allProcesses()
                .filter(ProcessHandle::isAlive)
                .filter(ProcessApiShowcase::isJavaProcess)
                .forEach(ProcessApiShowcase::printProcessInfo);

        // De ki vagyok én?
        System.out.println("Én vagyok: " + ProcessHandle.current().pid());
    }

    private static void printProcessInfo(ProcessHandle processHandle) {
        System.out.println(
                "PID: " + processHandle.pid() +
                ", command: " + processHandle.info().command().get() +
                ", user: " + processHandle.info().user().get() +
                ", start time: " + processHandle.info().startInstant().get() +
                ", CPU time: " + processHandle.info().totalCpuDuration().get() +
                ", arguments: " + String.join(", ", processHandle.info().arguments().orElse(new String[]{}))
        );
    }

    private static boolean isJavaProcess(ProcessHandle processHandle) {
        return processHandle.info().command().orElse("").contains("java");
    }
}
