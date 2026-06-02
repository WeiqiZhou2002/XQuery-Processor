for $b in document("test.xml")/library/book,
    $t in $b/title,
    $p in $b/price
return <r>{$t, $p}</r>