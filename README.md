# web-framework
For a college project

# Framework
Copiez le fichier framework.jar (dans le dossier framework de la branche master) dans le dossier WEB-INF/lib de votre application web

# Routes
Les urls qui veulent atteindre FrontServlet doivent se terminer par .fwk

# Vues
Pour rediriger l'application vers une vue, la méthode dans le modèle doit retourner un ModelView qui prend en paramètre:
- le nom de la page (que vous devez impérativement mettre dans le répertoire view de votre application)
- les données à envoyer. Passez le au ModelView avec la méthode addItem(String key, Object value) où key est le nom de la variable et value sa valeur

# Setter
Si vous voulez passez des variables de classe de la View vers le model:
- Le nom de la variable à passer au controller doit être exactement le même que celui de l'attribut de la classe concerné
- Si l'attribut est de type java.util.Date veillez à ce que le format de la valeur envoyée soit yyyy-MM-dd (annee-mois-jour) pour permettre le casting