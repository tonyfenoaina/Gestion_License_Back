let currentLang = 'fr'; // Langue par défaut

const translations = {
    'fr': translationsFR,
    'en': translationsEN
};

function changeLanguage(lang) {
    currentLang = lang;
    console.log(lang);
    
    const elements = document.querySelectorAll('[data-key]');
    
    elements.forEach((element) => {
        const key = element.getAttribute('data-key');
        element.textContent = translations[lang][key] || key;
    });

    // Mise à jour du titre de la page
    document.title = translations[lang]['pageTitle'];
}

// Chargement initial de la langue par défaut
document.addEventListener('DOMContentLoaded', () => {
    changeLanguage(currentLang);
});
