package designpatterns;



    //Polymorphism
    /*
    One of the key features of object oriented languages we are really interested in is Polymorphism
    Method that is going to be executed is not based on type that you think at compile time but the actual
    type at run time.

    Polymorphism has extensibility. If you want your code to be more extensible, you rely on abstraction and polymorphism.


    What is the worst keyword in Java from polymorphism point of view ?
    new - > This keyword is not polymorphic in Java, C# but it is polymorphic in Ruby
    When you are writing code in Ruby, you can have polymorphism very easily
    Some languages are so scared of new keyword that there is no new in python or Kotlin

    new says I want object of that specific class and that is tight coupling. new is not polymorphic in Java,
    C++ or C# but it is polymorphic in Ruby.


    So there are languages that dealt with new, there are languages that ran away from new and there are languages for which
    new is a disaster

    new ClassName() --> Tight coupling
     */

    // Interfaces cannot have non-final fields, in other words, interfaces cannot have states.
    // Whereas abstract classes can carry states.
    // You can think of interface as factory of methods

    interface PersonI {
        //Pet pet; // we cannot do this in interface
        // If this was an abstract base class, we can put a field inside abstract base class but interfaces cannot have
        // private fields
        // We could put fields inside abstract base class and that would work really well but interfaces cannot have private
        // fields in them. You cannot have private fields but you can have methods that would return the desired field.
        // In our case, we can have getPet() abstract method that would return Pet rather than having Pet as fields (Which
        // we cannot have in interfaces)
        // Instead of having a field in interface which you cannot, you can rather have abstract method that would in turn
        // return the field from the implementation that you may choose to use.

        default void play() {
            //System.out.println("Playing with :"+pet);
        }

    }

    interface Person {
        Pet getPet(); // rather than having a field you can have abstract method that can return a field from the
        // implementation
        default void play() {
            System.out.println("Playing with :"+getPet());
        }
    }

    class DogPerson implements Person {

        @Override
        public Pet getPet() {
            return new Dog();
        }
    }

    class CatLover implements Person {

        @Override
        public Pet getPet() {
            return new Cat();
        }
    }

    interface Pet {}
    class Dog implements Pet {}
    class Cat implements Pet {}



public class Factory {
    public static void main(String[] args) {
        call(new DogPerson());
        call(new CatLover());
    }

    public static void call(Person person) {
        person.play();
    }

    /*
    Think of interfaces as factory of methods
    You can write your methods in interface but anywhere your methods needs a prticular field
    delegate that to an abstract method which then pulls the data from the derived class.
    Now this leads to a very fundamental question i.e difference between an abstract factory and an abstract method

    Abstract method - alludes to the fact that you have a factory but the factory is a method and not class
    Abstract factory - relies on a class

    Factory Method : A class or an interface relies on a derived class that provide the implementation whereas the
    base provides common behaviour that you want to capture.
    So when you are using factory method, you are standing on the abstraction and looking down the hierarchy and relying on
    the derived class to provide the variations.

    In our example, play() is a common method but the delegation of getPet() method to the derived class to really
    override the behaviour and provide an alternative implementation.

    Factory method uses inheritance as a design tool, abstract factory on the other hand predominantly uses delegation as a
    design tool.

    Interfaces and default methods fits quite nicely in factory method pattern.


     */

}
