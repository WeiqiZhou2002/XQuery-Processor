for $b in document("test.xml")/library/book,
    $t in $b/title,
    $a in document("test.xml")/library/book,
    $t2 in $a/title
where $t eq $t2
return <r>{$b, $a}</r>