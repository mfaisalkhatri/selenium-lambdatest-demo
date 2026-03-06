package io.github.mfaisalkhatri.seleniumaidemo.testdatagenerator;

import net.datafaker.Faker;

public class GenerateTestData {

    public static void main (final String[] args) {
        final Faker faker = new Faker ();
        System.out.println ("Random First Name:" + faker.name ()
            .firstName ());
        System.out.println ("Random Last Name:" + faker.name ()
            .lastName ());
        System.out.println ("Random Email:" + faker.internet ()
            .emailAddress ());
        System.out.println ("Random Profession: " + faker.company ()
            .profession ());
    }
}
