for $b in document("test.xml")/library/book,
    $t in $b/title,
    $a in $b/author,
    $b2 in document("test.xml")/library/book,
    $t2 in $b2/title,
    $a2 in $b2/author
where $t eq $t2 and $a eq $a2
return <r>{$b, $b2}</r>