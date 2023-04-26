<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajout employé</title>
</head>
<body>
    <form action="/test-framework/emp-save.fwk" method="post">
        <input type="text" name="nom" placeholder="nom">
        <input type="text" name="adresse" placeholder="adresse">
        <label for="naissance">Date de naissance</label>
        <input type="date" name="naissance">
        <input type="number" name="enfant" placeholder="Nb enfant">
        <input type="submit" value="Valider">
    </form>
</body>
</html>