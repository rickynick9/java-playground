package designpatterns;

// Closed set of classes, any class outside cannot implement TrafficLight interface

public sealed interface TrafficLight {}

final class RedLight implements TrafficLight {}

final class GreenLight implements TrafficLight {}
