# Pavlova T.V. - TWA

Android-клиент для PWA-приложения [juristspb78.ru](https://juristspb78.ru).

Реализован через **Trusted Web Activity (TWA)** - официальный API Google для запуска PWA как нативного Android-приложения. Не WebView, не обёртка.

## Связь с основным проектом

- **PWA:** [prestige78/pavlova-tv-pwa](https://github.com/prestige78/pavlova-tv-pwa)
- **Домен:** [juristspb78.ru](https://juristspb78.ru)

## Зачем нужен APK

TWA-клиент собирается в **APK**, который публикуется в магазинах приложений (**RuStore**, Google Play и др.).

Это не альтернатива PWA - это **способ представить PWA на площадках**, где приложения распространяются только в виде APK. Пользователь устанавливает APK, а внутри работает та же PWA с полным офлайн-режимом.

## Параметры

| Параметр         | Значение                          |
| ---------------- | --------------------------------- |
| Package name     | `ru.juristspb78.app`              |
| App name         | Pavlova T.V.                      |
| minSdk           | 24 (Android 7.0)                  |
| targetSdk        | 36                                |
| compileSdk       | 37                                |
| Библиотека       | `androidbrowserhelper:2.5.0`      |
| LauncherActivity | `OfflineFirstTWALauncherActivity` |
| PWA-источник     | `https://juristspb78.ru`          |

## Что даёт TWA

- Полноэкранный режим без адресной строки Chrome
- Офлайн-режим из PWA (Service Worker + Cache API)
- Автоматическое обновление вместе с Chrome
- Проверка через Digital Asset Links (`assetlinks.json`)

## Безопасность

- `local.properties` - в `.gitignore` (пароли keystore)
- `keys/jurist78.jks` - вне проекта
- APK не в репозитории

## Публикация

- **RuStore:** `ru.juristspb78.app`
- **SHA-256:** `68:B4:2F:C0:2A:87:B5:03:63:A6:4D:DC:7D:8C:A8:B3:12:58:0F:B6:3C:90:E9:F2:E0:8E:E7:AE:36:F0:D6:C5`

## Лицензия

Для ИП Павлова Т.В., безвозмездно для использования на домене juristspb78.ru.
