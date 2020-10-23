package lab3.lib;

import java.util.ArrayList;

/***
 * @Author: Oleh Tymoshenko
 */

/**
 * Класс для вычисления значений параметров сигнала
 */
public class SignalParams {
    private byte[] _signal;

    /**
     * Создание объекта для вычисления значение сигнала
     * на основе массива байтов
     *
     * @param signal массив байтов, который представляет значения
     *               отсчетов сигнала в диапазоне [0;255]
     */
    public SignalParams(byte[] signal) {
        _signal = signal;
    }

    /**
     * Минимальное значение среди отсчетов в сигнале
     *
     * @return минимальный отсчет в сигнале
     */
    public byte signalMinVal()
    {
        byte minValInSignal = _signal[0];
        for(int i = 1; i < _signal.length; i++){
            if(minValInSignal > _signal[i])
                minValInSignal = _signal[i];
        }
        return minValInSignal;
    }

    /**
     * Максимальное значение среди отсчетов в сигнале
     *
     * @return максимальный отсчет в сигнале
     */
    public byte signalMaxVal()
    {
        byte maxValInSignal = _signal[0];
        for(int i = 1; i < _signal.length; i++){
            if(maxValInSignal < _signal[i])
                maxValInSignal = _signal[i];
        }
        return maxValInSignal;
    }

    /**
     * Динамический диапазон сигнала
     *
     * @return значение динамического диапазонана сигнала
     */
    public byte signalDynamicRange() {
        return (byte) (signalMaxVal() - signalMinVal());
    }

    /**
     * Энергия сигнала
     *
     * @return значение энергии сигнала
     */
    public double signalEnergy() {
        double res = 0;

        for (byte i:
             _signal) {
            res  = res + (Math.pow(i, 2));
        }

        return res;
    }

    /**
     * Средняя мощность сигнала
     *
     * @return средняя мощность сигнала
     */
    public  double signalAvgPower() {
        return  (signalEnergy()/_signal.length);
    }

    /**
     * Среднее значеие отсчетов сигнала
     *
     * @return среднее значение отсетов сигнала
     */
    public double signalAvgValue() {
        double res = 0;

        for (byte i:
                _signal) {
            res += i;
        }

        return res/_signal.length;
    }

    /**
     * Дисперсия значений отсчетов сигнала
     *
     * @return дисперсия значений отсчетов
     */
    public double signalDispersion() {
        double signalAvgVal = signalAvgValue();
        double res = 0;

        for (byte i:
             _signal) {
            res += Math.pow((i - signalAvgVal), 2);
        }

        return res/_signal.length;
    }

    /**
     * Функция автокорреляции
     *
     * @param tau - тау для функции автокорреляции
     * @return значение функции автокорреляции для заданного тау
     */
    public double signalAutocorrelationFunc(int tau) {
        double avgSignalVal = signalAvgValue();
        double res = 0;

        if(tau < 0) {
            res = signalAutocorrelationFunc(0-tau);
        }
        else{
            double sum = 0;
            for(int i = 0; i < _signal.length - tau; i++) {
                sum += ((_signal[i+tau] - avgSignalVal)*
                        (_signal[i] - avgSignalVal));
            }
            res = sum / (_signal.length - tau);
        }

        return res;
    }

    /**
     * Интервал кореляции
     *
     * @return интервал кореляции
     */
    public double signalCIntervalCorrelation() {
        double res = 0;

        double numerator = 0;
        for(int i = 0; i < _signal.length; i++) {
            numerator += signalAutocorrelationFunc(i);
        }

        res = numerator / signalAutocorrelationFunc(0);

        return Math.ceil(Math.abs(res));
    }

    /**
     * Возвращает все параметры в удобочитаемом виде
     *
     * @return строка, представляющая все параметры
     */
    public String toStringValues(int tau) {
        String resStr = "Мінімальне значення відліка в сигналі: " +  signalMinVal() + "\n" +
                        "Максимальне значення відліка в сигналі: " +  signalMaxVal() + "\n" +
                        "Динамічний діапазон сигналу: " +  signalDynamicRange() + "\n" +
                        "Енергія сигналу: " +  signalEnergy() + "\n" +
                        "Середня потужність сигналу: " +  signalAvgPower() + "\n" +
                        "Середнє значення відліків сигналу: " +  signalAvgValue() + "\n" +
                        "Дисперсія значень відліків сигналу: " +  signalDispersion() + "\n" +
                        "Функція автокореляці дискретного сигналу (тау = " + tau + "): " +  signalAutocorrelationFunc(tau) + "\n" +
                        "Інтервал кореляції: " +  signalCIntervalCorrelation() + "\n";
        return resStr;
    }

}
