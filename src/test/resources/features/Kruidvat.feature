Feature: Cart feature

  Scenario: Add a product to the cart
    Given User created a cart via api
    When User add the product '2876350' to cart with 1 quantity via api
    Then 1 product '2876350' is added to cart via api
    When User opens Home Page
    And Authenticate to web application by adding 'kvn-cart' cookie
    And User opens Basket Page
    Then Basket contains 1 expected product

