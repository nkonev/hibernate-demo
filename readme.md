CascadeType.ALL causes 3 join on save independently of enhancement
@OneToOne(fetch = FetchType.LAZY) is working without enhancement - for @OneToOne Hibernate produces two selects instead join
It's enough to MutableList#add(Person) in order to add a person to it's table and link table