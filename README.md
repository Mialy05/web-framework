# web-framework

-Pour l'instant, à tester sur Windows. (Cause: le mapping d'url vers classe de mapping n'est pas encore au point pour les autres plateformes car les chemins d'accès sont un peu différents pour chaque plateforme )

# Framework

-Copiez le fichier framework.jar (dans le dossier framework de la branche master) dans le dossier WEB-INF/lib de votre application web.
-Mettez aussi un servlet-api.jar dans le dossier de librairie de votre application

# Web.xml

Déclarer le servlet FrontServlet dans le fichier WEB-INF/web.xml tel que:

- servlet-name: FrontServlet
- servlet-class: etu1834.framework.servlet.FrontServlet
- url-pattern: \*.fwk

Par conséquent, les urls qui veulent atteindre FrontServlet doivent se terminer par l'extension .fwk

```xml
<servlet>
      <servlet-name>FrontServlet</servlet-name>
      <servlet-class>etu1834.framework.servlet.FrontServlet</servlet-class>
    </servlet>

<!-- SERVLET MAPPING -->
    <servlet-mapping>
      <servlet-name>FrontServlet</servlet-name>
      <url-pattern>*.fwk</url-pattern>
    </servlet-mapping>
</servlet>
```

# Routes

Les urls qui veulent atteindre FrontServlet doivent se terminer par .fwk

# Modèle

- Vos classes doivent avoir des getter et setter : - getNomAttribut() - setNomAttribut(params)
  où NomAttribut correspond au nom de l'attribut de classe concerné (la première lettre en majuscule)
  Pour permettre le mapping entre un url et une méthode:
- Annotez la méthode avec l'annotation @Url(url="URL_APPELANT_LA_METHODE")
  exemple:

```java
    @Url(url = "liste.fwk")
    public void getListe() {
        // code
    }
```

# Arborescence de votre application
<ul>
    <li>
        views
        <ul>
            <li> Les pages .jsp </li>
        </ul>
    </li>
    <li>
        WEB-INF
        <ul>
            <li> classes </li>
            <li> 
                lib 
                <ul>
                    <li> framework.jar </li>
                    <li> servlet-api.jar </li>
                </ul>
            </li>
            <li> web.xml </li>
        </ul>
    </li>
</ul>

# Vues

Mettez vos pages jsp dans un dossier <strong>"views"</strong>  dans votre application, au même niveau que WEB-INF.
Pour rediriger l'application vers une vue, la méthode dans le modèle doit retourner un ModelView. Un ModelView possède deux attributs:

- le nom de la page sans l'extension .jsp ( que vous devez impérativement mettre dans le répertoire views de votre application) à ajouter avec le setter ModelView.setView("#nom de la page sans .jsp")
- les données à envoyer. Passez le au ModelView avec la méthode addItem(String key, Object value) où key est le nom de la variable et value sa valeur
```java
    @Url(url = "liste.fwk")
    public Modelview getListe() {
        ModelView view = new ModelView();
        view.setView("liste");
        view.addItem("numero", 7);
        return view;
    }
```
- pour récupérer une variable, utilisez request.getAttribute("key") où key correspond au nom de la variable que vous avez envoyé depuis la méthode d'action
- veillez à caster la variable passer dans votre Vue.

Dans la page views/liste.jsp
```jsp
    Numéro <%= request.getAttribute("numero") %>
```

# Passage de variable de la vue à la méthode d'action

Le framework offre deux moyens de passer des variables de la vue vers le modèle:

- Attribuer les paramètres de la vue aux variables de classe du modèle:
    - Le nom de la variable à passer doit être exactement le même que celui de l'attribut de la classe concerné, le framework s'occupera de caster les variables
    - ATTENTION : Si la variable envoyée est null ou est un string vide, le framework renverra une variable null à la méthode d'action
    - Si l'attribut est de type java.util.Date veillez à ce que le format de la valeur envoyée soit yyyy-MM-dd (annee-mois-jour) pour permettre le cast

    exemple:
    ```jsp
        <form action="emp-save.fwk" method="post">
            <input type="text" name="nom" placeholder="nom">
            <input type="text" name="adresse" placeholder="adresse">
            <label for="naissance">Date de naissance</label>
            <input type="date" name="naissance">
            <input type="submit" value="Valider">
        </form> 
    ```
    Nous avons la classe Emp comme classe de Mapping (avec ces setters et ces getters).
    <br>
    Nous allons envoyer les données de ce formulaire à la methode save de la classe Emp qui a déjà été décorée avec l'annotation Url.
    ```java
        public class Emp {
            int id;
            String nom;
            String adresse;
            Date naissance;
            int enfant;

            // getter
            //setter
    
            public ModelView save() {
                ModelView view = new ModelView();
                view.setView("info");
                view.addItem("nom", this.getNom());
                view.addItem("adresse", this.getAdresse());
                view.addItem("naissance", this.getNaissance());
                return view;
            }   
        }
    ```

- Utiliser les variables envoyés comme argument de méthode dans la classe de Mapping:
    - Annotez l'argument de la méthode concerné par la liaison de variable avec le décorateur @Param(name="NOM_DE_LA_VARIABLE_DANS_VIEW")
    - Utilisez-le librement dans votre méthode

    exemple 
    ```jsp
        <a href="details.fwk?id=1">1</a>
    ```
    Nous avons besoin de ce paramètre "id" dans notre méthode details dans la classe de Mappint
    ```java
        @Url(url = "details.fwk")
        public ModelView details(@Param(name = "id") int id, String name) {
            // comme le paramètre id est annoté, il est lié au variable venant de la vue
            // ce qui n'est pas le cas pour le paramètre name
            ModelView view = new ModelView();
            view.setView("details");
            view.addItem("numero", id);
            view.addItem("name", name);
            return view;
        }
    ```

# Singleton 
Si vous voulez que votre classe soit un singleton, annotez la classe avec le décorateur Scope(package etu1834.framework.decorator.Scope, veillez à l'importer correctement ). Ce décorateur a un attribut <strong>value</strong> et sa valeur doit être "singleton". 

```java
    @Scope(value = "singleton")
    public class Emp {
        int id;
        String nom;
        String adresse;

        // code ...
    }
```
# Protection de méthode avec Session 
Pour utiliser HttpSession, vous devez ajoutez quelques lignes à votre web.xml à l'intérieur de la balise servlet contenant FrontServlet
```xml
    <servlet>
      <servlet-name>Controller</servlet-name>
      <servlet-class>etu1834.framework.servlet.FrontServlet</servlet-class>

      <init-param>
        <param-name>sessionName</param-name>
        <param-value>{{ nom de votre variable de session }}</param-value>
      </init-param>
    </servlet>  
```
Ici, nous avons défini notre variable de session. C'est cette valeur que vous utiliserez comme attribut de votre session. Veillez à bien copiez la balise <strong>pram-name</strong>, et remplacez le contenu de <strong>param-value</strong> par le nom de variable que vous voudriez utiliser.

Si votre fonction veut ajouter des variables de session, elle doit retourner un ModelView. Veillez à ajouter vos variables de session et leurs valeurs dans l'attribut <strong>session</strong> de l'objet ModelView à retourner avec la méthode addSession(String nomVariable, Object value).
<strong>IMPORTANT</strong>
Le nom de variable de session doit correspondre à celui que vous avez défini dans web.xml
Nous avons par exemple une méthode de connexion
```java
    @Url(url = "connection.fwk")
    public ModelView connection() {
        ModelView view = new ModelView();
        view.setView("hello");
        view.addSession("profil", "user");
        return view;
    }
```
et dans notre web.xml 
```xml
    <servlet>
      <servlet-name>Controller</servlet-name>
      <servlet-class>etu1834.framework.servlet.FrontServlet</servlet-class>

      <init-param>
        <param-name>sessionName</param-name>
        <param-value>profil</param-value>
      </init-param>
    </servlet>  
```
Ainsi nous avons défini une variable de session "profil" qui contient la valeur "user"
Cette dernière sera alors utilisée pour protéger vos méthodes.
Utilisez l'annotation @Auth(profil = "") sur les méthodes que vous voulez protéger.
La variable profil de l'annotation correspond au profil autorisé à accéder à ce méthode.

# 1.Session
Si vous voulez ajouter, modifier ou manipulez des variables de session dans votre méthode, suivez les étapes suivantes:
 - Ajouter un attribut nommé <strong>session</strong> de type HashMap<String, Object> dans votre classe
 - Décorer la méthode qui veut utiliser des variables de session avec <strong>@Session</strong>
 - Les variables de sessions seront tout de suite présentes dans l'attribut <strong>session</strong> de votre classe
 - De même, les modifications que vous apporterez dans l'attribut session de votre classe sera pris en compte dans la session de l'application.

# 2.Session
Le ModelView a aussi un attribut HashMap<String, Object> session pour ajouter ou modifier les variables de session dans HttpSession de toute l'application. Donc si vous voulez utiliser, ajouter ou modifier des variables de session dans votre application, vous pouvez manipulez cet attribut. Mais si vous voulez récupérer des variables de session, optez  pour l'option précédente.

# 3. Session: suppression
Pour supprimer tous les variables de session, changer la valeur de l'attribut <strong>invalidateSession</strong> du ModelView à retourner en true avec son setter setInvalidateSession.
Pour supprimer une variable spécifique, ajoutez son nom dans l'attribut <strong>removeSession</strong> qui est un tableau de String avec la méthode addRemoveSession(string)

