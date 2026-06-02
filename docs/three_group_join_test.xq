for $b1 in document("test.xml")/library/book,
    $t1 in $b1/title,
    $b2 in document("test.xml")/library/book,
    $t2 in $b2/title,
    $b3 in document("test.xml")/library/book,
    $t3 in $b3/title
where $t1 eq $t2 and $t2 eq $t3
return <r>{$b1, $b2, $b3}</r>