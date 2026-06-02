for $tuple in join(
  (for $b in document("test.xml")/library/book,
       $t in $b/title
   return <tuple>{
     <b>{$b}</b>,
     <t>{$t/text()}</t>
   }</tuple>),

  (for $b2 in document("test.xml")/library/book,
       $t2 in $b2/title
   return <tuple>{
     <p>{$b2/price}</p>,
     <t2>{$t2/text()}</t2>
   }</tuple>),

  [t],
  [t2]
)
return <joined>{
  <title>{$tuple/b/*/title/text()}</title>,
  <price>{$tuple/p/*/text()}</price>
}</joined>